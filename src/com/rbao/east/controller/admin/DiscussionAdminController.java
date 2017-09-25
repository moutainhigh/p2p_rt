package com.rbao.east.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rbao.east.common.page.PageModel;
import com.rbao.east.common.result.DwzResult;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Answer;
import com.rbao.east.entity.DiscussionConfig;
import com.rbao.east.entity.Question;
import com.rbao.east.entity.SysModule;
import com.rbao.east.entity.Tag;
import com.rbao.east.entity.User;
import com.rbao.east.service.AnswerService;
import com.rbao.east.service.AnswerUserService;
import com.rbao.east.service.DiscussionConfigService;
import com.rbao.east.service.QuestionService;
import com.rbao.east.service.QuestionTagService;
import com.rbao.east.service.QuestionUserService;
import com.rbao.east.service.SysModuleService;
import com.rbao.east.service.TagService;
import com.rbao.east.utils.SpringUtils;

/**
 * 问答管理
 */
@Controller
@RequestMapping("dis")
public class DiscussionAdminController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(DiscussionAdminController.class);

	@RequestMapping("answers")
	public String answers(HttpServletRequest request, Model model) {
		Map<String, String> maps = this.getParameters(request);
		PageModel pm = answerService.getPage(maps);

		maps.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList = moduleService.getRightGroupList(maps);

		model.addAttribute("pm", pm);
		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("searchParams", maps);
		model.addAttribute("right_id", maps.get("right_id"));
		model.addAttribute("hidden", maps.get("hidden"));
		return "discussion/answers";
	}

	@RequestMapping("answer/edit")
	public String answerEdit(HttpServletRequest request, Model model) {
		Map<String, String> params = this.getParameters(request);
		Answer answer = answerService.selectByPrimaryKey(Integer
				.parseInt(params.get("answerId")));
		Question question = questionService.selectByPrimaryKey(answer
				.getQuestionId());

		model.addAttribute("question", question);
		model.addAttribute("answer", answer);
		model.addAttribute("right_id", params.get("right_id"));
		return "discussion/answerEdit";
	}

	@RequestMapping("answer/saveOrUpdate")
	public void answerSaveOrUpdate(Answer answer, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, String> params = this.getParameters(request);
		boolean flag = false;
		try {
			answerService.saveOrUpdate(answer);
			flag = true;
		} catch (Exception e) {
			logger.error("更新/添加异常", e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, params.get("right_id")
						.toString());
	}

	@RequestMapping("answer/delete")
	public void answerDelete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		Integer aid = Integer.parseInt(params.get("answerId"));
		boolean flag = false;
		try {
			answerService.deleteByPrimaryKey(aid);
			answerUserService.deleteByAnswerId(aid);
			flag = true;
		} catch (Exception e) {
			logger.error("delete异常", e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常");
	}
	
	/**
	 * 后台添加回答
	 */
	@RequestMapping("question/addAnswer")
	public String questionAnswer(HttpServletRequest request,Model model){
		Map<String, String> params = this.getParameters(request);
		Question question = questionService.selectByPrimaryKey(Integer
				.parseInt(params.get("questionId")));
		
		model.addAttribute("question", question);
		model.addAttribute("right_id", params.get("right_id"));
		return "discussion/addAnswer";
	}
	
	@RequestMapping("question/saveAnswer")
	public void saveAnswer(HttpServletRequest request,HttpServletResponse response){
		User user = this.loginAdminUser(request);
		Map<String, String> params = this.getParameters(request);
		boolean flag = false;
		try {
			Question question = questionService.selectByPrimaryKey(Integer.parseInt(params.get("questionId")));
			String content = params.get("content");
			
			if (StringUtils.isNotEmpty(content)) {
				
				Answer answer = new Answer();
				answer.setAnswerDate(new Date());
				answer.setContent(content);
				answer.setQuestionId(question.getId());
				answer.setUserName(user.getUserAccount());
				answer.setUserId(user.getId());
				answer.setHidden(0);
				
				answerService.saveOrUpdate(answer);
				
				question.setReplyNum(question.getReplyNum() + 1);
				questionService.saveOrUpdate(question);
				
				flag = true;
				
			}
		} catch (Exception e) {
			logger.error("保存异常",e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, params.get("right_id")
						.toString());
	}

	@RequestMapping("questions")
	public String questions(HttpServletRequest request, Model model) {
		Map<String, String> maps = this.getParameters(request);
		PageModel pm = questionService.getPage(maps);
		maps.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList = moduleService.getRightGroupList(maps);

		model.addAttribute("pm", pm);
		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("searchParams", maps);
		model.addAttribute("right_id", maps.get("right_id"));
		model.addAttribute("hidden", maps.get("hidden"));
		return "discussion/questions";
	}

	@RequestMapping("question/edit")
	public String questionEdit(Model model, HttpServletRequest request) {
		Map<String, String> params = this.getParameters(request);
		Question question = questionService.selectByPrimaryKey(Integer
				.parseInt(params.get("questionId")));

		model.addAttribute("question", question);
		model.addAttribute("right_id", params.get("right_id"));
		return "discussion/questionEdit";
	}

	@RequestMapping("question/saveOrUpdate")
	public void questionSaveOrUpdate(Question question,
			HttpServletRequest request, HttpServletResponse response) {
		boolean flag = false;
		Map<String, String> maps = this.getParameters(request);
		try {
			questionService.saveOrUpdate(question);
			flag = true;
		} catch (Exception e) {
			logger.error("question添加/修改异常", e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, maps.get("right_id")
						.toString());
	}

	@RequestMapping("question/answers")
	public String questionAnswers(HttpServletRequest request, Model model) {
		Map<String, String> params = this.getParameters(request);
		PageModel pm = answerService.getPage(params);
		Integer qid = Integer.parseInt(params.get("questionId"));
		Question question = questionService.selectByPrimaryKey(qid);

		model.addAttribute("pm", pm);
		model.addAttribute("searchParams", params);

		model.addAttribute("right_id", params.get("right_id"));
		model.addAttribute("questionId", qid);
		model.addAttribute("question", question);
		return "discussion/questionAnswers";
	}

	@RequestMapping("question/answer/edit")
	public String questionAnswerEdit(HttpServletRequest request, Model model) {
		Map<String, String> params = this.getParameters(request);
		Answer answer = answerService.selectByPrimaryKey(Integer
				.parseInt(params.get("answerId")));

		model.addAttribute("answer", answer);
		model.addAttribute("right_id", params.get("right_id"));
		return "discussion/questionAnswerEdit";
	}

	// TODO
	@RequestMapping("question/answer/saveOrUpdate")
	public void questionAnswerSaveOrUpdate(HttpServletRequest request,
			Answer answer, HttpServletResponse response) {
		Map<String, String> maps = this.getParameters(request);
		boolean flag = false;
		try {
			answerService.saveOrUpdate(answer);
			flag = true;
		} catch (Exception e) {
			logger.error("保存异常", e);
			flag = false;
		}

		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, maps.get("right_id")
						.toString());
	}

	@RequestMapping("question/answer/delete")
	public void questionAnswerDelete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> maps = this.getParameters(request);
		Integer aid = Integer.parseInt(maps.get("answerId"));
		boolean flag = false;
		try {
			boolean b1 = answerService.deleteByPrimaryKey(aid);
			boolean b2 = answerUserService.deleteByAnswerId(aid);
			flag = true;
		} catch (Exception e) {
			logger.error("delete异常", e);
			flag = false;
		}

		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常");
	}

	// TODO
	@RequestMapping("question/delete")
	public void questionDelete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> maps = this.getParameters(request);
		boolean flag = false;
		try {
			Integer qid = Integer.parseInt(maps.get("questionId"));
			boolean b1 = questionService.deleteByPrimaryKey(qid);
			boolean b2 = questionTagService.deleteByQuestionId(qid);
			boolean b3 = questionUserService.deleteByQuestionId(qid);
			boolean b5 = answerUserService.deleteByQuestionId(qid);
			boolean b4 = answerService.deleteByQuestionId(qid);

			flag = true;
		} catch (Exception e) {
			logger.error("question-删除失败", e);
			flag = false;
		}

		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常", "",
				maps.get("right_id").toString(),
				"dic/nation/getNationList?right_id="
						+ maps.get("right_id").toString());
	}

	@RequestMapping("tags")
	public String tags(Model model, HttpServletRequest request) {
		Map<String, String> maps = this.getParameters(request);
		maps.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList = moduleService.getRightGroupList(maps);
		PageModel pm = tagService.getPage(maps);

		model.addAttribute("pm", pm);
		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("searchParams", maps);
		model.addAttribute("right_id", maps.get("right_id"));
		return "discussion/tags";
	}

	@RequestMapping("tag/add")
	public String tagAdd(HttpServletRequest request, Model model) {
		Map<String, String> params = this.getParameters(request);
		model.addAttribute("right_id", params.get("right_id"));
		return "discussion/tagAdd";
	}

	@RequestMapping("tag/edit")
	public String tagEdit(HttpServletRequest request, Model model) {
		Map<String, String> params = this.getParameters(request);
		Tag tag = tagService.selectByPrimaryKey(Integer.parseInt(params
				.get("tagId")));
		if (tag != null) {
			model.addAttribute("tag", tag);
		}
		model.addAttribute("right_id", params.get("right_id"));
		return "discussion/tagEdit";
	}

	@RequestMapping("tag/saveOrUpdate")
	public void tagSaveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, Tag tag) {
		boolean flag = false;
		Map<String, String> maps = this.getParameters(request);
		try {
			tagService.saveOrUpdate(tag);
			flag = true;
		} catch (Exception e) {
			logger.error("tag添加/修改异常", e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, maps.get("right_id")
						.toString());
	}

	@RequestMapping("tag/delete")
	public void tagDelete(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> maps = this.getParameters(request);
		boolean flag = false;
		try {
			Integer id = Integer.parseInt(maps.get("tagId"));
			flag = tagService.deleteByPrimaryKey(id);
		} catch (Exception e) {
			logger.error("tag删除异常", e);
			flag = false;
		}

		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常", "",
				maps.get("right_id").toString(),
				"dic/nation/getNationList?right_id="
						+ maps.get("right_id").toString());
	}

	@RequestMapping("tag/checkExist")
	public void tagCheckExist(HttpServletRequest request,
			HttpServletResponse response) {
		String msg = "0";
		Map<String, String> maps = this.getParameters(request);
		List<Tag> tags = tagService.checkCode(maps);
		if (tags.size() > 0) {
			msg = "1";
		}
		SpringUtils.renderJson(response, msg);
	}

	/**
	 * 问答设置
	 */
	@RequestMapping("configs")
	public String configs(HttpServletRequest request, Model model) {
		Map<String, String> params = this.getParameters(request);
		params.put("userId", this.loginAdminUser(request).getId().toString());
		List<SysModule> righSubtList = moduleService.getRightGroupList(params);
		PageModel pm = configService.getPage(params);

		model.addAttribute("righSubtList", righSubtList);
		model.addAttribute("right_id", params.get("right_id"));
		model.addAttribute("pm", pm);
		model.addAttribute("searchParams", params);
		return "discussion/configs";
	}

	@RequestMapping("config/edit")
	public String configEdit(Model model, HttpServletRequest request) {
		Map<String, String> params = this.getParameters(request);
		model.addAttribute("right_id", params.get("right_id"));
		DiscussionConfig config = configService.selectByPrimaryKey(Integer
				.parseInt(params.get("configId")));
		if (config != null) {
			model.addAttribute("config", config);
		}
		model.addAttribute("right_id", params.get("right_id"));
		return "discussion/configEdit";
	}

	@RequestMapping("config/saveOrUpdate")
	public void configSaveOrUpdate(HttpServletRequest request,
			HttpServletResponse response, DiscussionConfig config) {
		boolean flag = false;
		Map<String, String> maps = this.getParameters(request);
		try {
			configService.saveOrUpdate(config);
			flag = true;
		} catch (Exception e) {
			logger.error("config添加/修改异常", e);
			flag = false;
		}
		SpringUtils.renderDwzResult(response, flag, flag ? "操作成功" : "操作异常",
				DwzResult.CALLBACK_CLOSECURRENT, maps.get("right_id")
						.toString());
	}

	// --------------------------------------------------------------------

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionTagService questionTagService;

	@Autowired
	private QuestionUserService questionUserService;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private AnswerUserService answerUserService;

	@Autowired
	private TagService tagService;

	@Autowired
	private SysModuleService moduleService;

	@Autowired
	private DiscussionConfigService configService;

}
