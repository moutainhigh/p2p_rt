package com.rbao.east.controller.front;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rbao.east.common.page.PageModel;
import com.rbao.east.controller.BaseController;
import com.rbao.east.entity.Answer;
import com.rbao.east.entity.AnswerUser;
import com.rbao.east.entity.DiscussionConfig;
import com.rbao.east.entity.Question;
import com.rbao.east.entity.QuestionTag;
import com.rbao.east.entity.QuestionUser;
import com.rbao.east.entity.Tag;
import com.rbao.east.entity.User;
import com.rbao.east.service.AnswerService;
import com.rbao.east.service.AnswerUserService;
import com.rbao.east.service.DiscussionConfigService;
import com.rbao.east.service.QuestionService;
import com.rbao.east.service.QuestionTagService;
import com.rbao.east.service.QuestionUserService;
import com.rbao.east.service.TagService;
import com.rbao.east.service.UserService;
import com.rbao.east.utils.DateUtils;
import com.rbao.east.utils.SpringUtils;
/**
 * 问答管理
 * */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("/discussion")
public class DiscussionController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(DiscussionController.class);

	private static final String QUESTION_AUDIT = "QUESTION_AUDIT";
	private static final String ANSWER_AUDIT = "ANSWER_AUDIT";

	private static final String INDEX = "discussion/index";
	private static final String ASK = "discussion/ask";
	private static final String ANSWER = "discussion/answer";
	private static final String USER = "discussion/user";

	@RequestMapping("updateBrowser.do")
	public String updateBrowser(){
		return "discussion/updateBrowser";
	}
	
	/**
	 * 问答首页
	 */
	@RequestMapping({ "/", "index.do" })
	public String index(Model model, HttpServletRequest request) {
		User user = this.loginFrontUser(request);
		Integer unReadQuestionsCount = 0;
		if (user != null) {
			// TODO

			Map<String, Object> params = Maps.newHashMap();
			params.put("hidden", 0);
			List<Integer> quids = questionUserService
					.getUserhaveSeeQuestionIds(user.getId());
			if (quids.size() > 0) {
				params.put("ids", quids);
				unReadQuestionsCount = questionService
						.getUnreadQuestionsCount(params);
			} else {
				Map<String, String> params2 = Maps.newHashMap();
				params2.put("hidden", "0");
				unReadQuestionsCount = questionService
						.getQuestionsCount(params2);
			}
		}

		Integer totalQuestionsCount = questionService
				.getQuestionsCount(new HashMap<String, String>());
		Integer totalUsersCount = questionService.getQuestionUsersCount()
				+ answerService.getAnswerUserCount();

		Map<String, String> params = Maps.newHashMap();
		params.put("hidden", "0");
		params.put("replyNum", "0");
		Integer notAnswerQuestionsCount = questionService
				.getQuestionsCount(params);

		request.getSession().setAttribute("totalQuestionsCount",
				totalQuestionsCount);
		request.getSession().setAttribute("totalUsersCount", totalUsersCount);
		request.getSession().setAttribute("unReadQuestionsCount",
				unReadQuestionsCount);
		request.getSession().setAttribute("notAnswerQuestionsCount",
				notAnswerQuestionsCount);
		return INDEX;
	}

	/**
	 * 根据用户id获取其数据
	 */
	@RequestMapping("user/{uid}.do")
	public String user(@PathVariable Integer uid, Model model,HttpServletRequest request) {
		User loginuser = this.loginFrontUser(request);
		if (loginuser == null) {
			return "redirect:../../login";
		}
		try {
			User user = userService.getById(uid);

			Map<String, String> params = Maps.newHashMap();
			params.put("hidden", "0");
			params.put("userId", "" + uid);
			Integer userQuestionCount = questionService
					.getQuestionsCount(params);
			Integer userAnswerCount = answerService.getAnswersCount(params);

			model.addAttribute("user", user);
			model.addAttribute("userQuestionCount", userQuestionCount);
			model.addAttribute("userAnswerCount", userAnswerCount);
		} catch (Exception e) {
			logger.error("获取用户信息异常--ID--" + uid, e);
			return INDEX;
		}
		return USER;
	}

	// TODO
	@RequestMapping("getUserQuestionData.do")
	public void getUserQuestionsData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		params.put("hidden", "0");
		PageModel pm = questionService.getPage(params);
		SpringUtils.renderJson(response, pm);
	}

	@RequestMapping("getUserAnswerQuestionData.do")
	public void getUserAnswerQuestion(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		params.put("hidden", "0");
		PageModel pm = questionService.getUserAnswerQuestionPage(params);
		SpringUtils.renderJson(response, pm);
	}

	/**
	 * 获取登录用户未读问题数据
	 */
	@RequestMapping("getUnreadQuestionData.do")
	public void getUnreadQuestionData(HttpServletRequest request,
			HttpServletResponse response) {
		User user = this.loginFrontUser(request);

		Map<String, Object> params = this.getParametersO(request);
		Map<String, String> params2 = this.getParameters(request);
		params.put("hidden", "0");// 未被设置隐藏
		params2.put("hidden", "0");// 未被设置隐藏
		if (user != null) {
			List<Integer> quids = questionUserService
					.getUserhaveSeeQuestionIds(user.getId());
			if (quids.size() > 0) {
				params.put("ids", quids);

				PageModel pm = questionService.getUnreadQuestionPage(params);
				List<Question> questions = pm.getList();
				setQuestionTag(questions);
				pm.setList(questions);

				SpringUtils.renderJson(response, pm);
			} else {
				PageModel pm = questionService.getPage(params2);
				List<Question> questions = pm.getList();
				setQuestionTag(questions);
				pm.setList(questions);

				SpringUtils.renderJson(response, pm);
			}

		}
	}

	/**
	 * 搜索
	 */
	@RequestMapping("search.do")
	public String search(HttpServletRequest request, Model model) {
		String keyword = request.getParameter("keyword");
		if (StringUtils.isEmpty(keyword)) {
			return INDEX;
		}
		Map<String, String> params = Maps.newHashMap();
		params.put("content", keyword);
		Integer record = questionService.getQuestionsCount(params);

		model.addAttribute("record", record);
		model.addAttribute("keyword", keyword);
		return "discussion/searchByWord";
	}

	/**
	 * 根据tag的id获取所有的问题
	 */
	@RequestMapping("tag/{tagId}.do")
	public String tag(@PathVariable Integer tagId, Model model) {
		try {
			Tag tag = tagService.selectByPrimaryKey(tagId);

			model.addAttribute("tag", tag);
		} catch (Exception e) {
			logger.error("获取问题异常--ID--" + tagId, e);
			return INDEX;
		}
		return "discussion/searchByTag";
	}

	@RequestMapping("getTagQuestionsData.do")
	public void getQuestionsData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);

		params.put("hidden", "0");
		PageModel pm = questionService.getQuestionByTagPage(params);
		List<Question> questions = pm.getList();
		setQuestionTag(questions);
		pm.setList(questions);
		SpringUtils.renderJson(response, pm);
	}

	@RequestMapping("question/{qid}.do")
	public String question(@PathVariable Integer qid, Model model,
			HttpServletRequest request) {
		try {
			List<Answer> newAnswers = Lists.newArrayList();
			Question question = questionService.selectByPrimaryKey(qid);
			Map<String, String> params = Maps.newHashMap();
			params.put("questionId", "" + qid);
			List tags = questionTagService.getTagsByQid(qid);

			params.put("hidden", "0");
			List<Answer> answers = answerService.getAnswers(params);
			for (Answer answer : answers) {
				answer.setUserImg(userService.selectByPrimaryKey(
						answer.getUserId()).getAvatarImg());
				newAnswers.add(answer);
			}

			model.addAttribute("question", question);
			model.addAttribute("tags", tags);
			model.addAttribute("answers", newAnswers);
			model.addAttribute("user",
					userService.selectByPrimaryKey(question.getUserId()));

			question.setBroswerNum(question.getBroswerNum() + 1);// 增加浏览量
			questionService.saveOrUpdate(question);

			// TODO
			// 设置用户与问题查看关系
			User user = this.loginFrontUser(request);
			if (user != null) {
				Map<String, String> quparams = Maps.newHashMap();
				quparams.put("questionId", "" + qid);
				quparams.put("userId", "" + user.getId());
				List<QuestionUser> qus = questionUserService
						.getQuestionUsers(quparams);
				if (qus.size() < 1) {// 若不存在则添加
					QuestionUser qUser = new QuestionUser();
					qUser.setQuestionId(qid);
					qUser.setUserId(user.getId());
					qUser.setHasread(1);
					questionUserService.saveOrUpdate(qUser);
				}
			}
		} catch (Exception e) {
			logger.error("获取问题异常--ID--" + qid, e);
			return INDEX;
		}
		return ANSWER;
	}

	/**
	 * 处理添加回答
	 */
	@RequestMapping("answerHandler.do")
	public String answerHandler(HttpServletRequest request) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			return "redirect:../login";
		}
		Map<String, String> params = this.getParameters(request);
		Integer questionId = Integer.parseInt(params.get("questionId"));
		String content = params.get("content");
		if (StringUtils.isEmpty(content)) {
			return "redirect:question/" + questionId + ".do";
		}

		try {
			Answer answer = new Answer();
			answer.setAnswerDate(new Date());
			answer.setContent(content);
			answer.setQuestionId(questionId);
			answer.setUserName(user.getUserAccount());
			answer.setUserId(user.getId());
			if (isAnswerAudit()) {
				answer.setHidden(1);
			} else {
				answer.setHidden(0);
			}
			answerService.saveOrUpdate(answer);

			Question question = questionService.selectByPrimaryKey(questionId);
			question.setReplyNum(question.getReplyNum() + 1);
			questionService.saveOrUpdate(question);
		} catch (Exception e) {
			logger.error("处理添加回答异常", e);
		}

		return "redirect:question/" + questionId + ".do";
	}

	@RequestMapping("getQuestionData.do")
	public void getQuestionData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = this.getParameters(request);
		params.put("hidden", "0");
		PageModel pm = questionService.getPage(params);
		List<Question> questions = pm.getList();
		setQuestionTag(questions);
		pm.setList(questions);
		SpringUtils.renderJson(response, pm);
	}

	/**
	 * 添加问题页面
	 */
	@RequestMapping("ask.do")
	public String ask(Model model) {
		Map<String, String> params = Maps.newHashMap();
		params.put("0", "hidden");// 未被设置隐藏
		List<Tag> tagList = tagService.getTags(params);
		model.addAttribute("tagList", tagList);
		return ASK;
	}

	/**
	 * 处理添加问题
	 */
	@RequestMapping("askHandler.do")
	public String askHandler(HttpServletRequest request, Model model) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			return "redirect:../login";
		}

		Map<String, String> maps = this.getParameters(request);
		String content = maps.get("content");
		String title = maps.get("title");
		if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content)) {
			model.addAttribute("msg", "输入有误!");
			return ASK;
		}

		Question question = new Question();
		question.setQuestionDate(new Date());
		question.setTitle(title);
		question.setContent(content);
		question.setUserName(user.getUserAccount());
		question.setUserId(user.getId());
		if (isQuestionAudit()) {// 是否需要审核
			question.setHidden(1);
		} else {
			question.setHidden(0);
		}
		questionService.saveOrUpdate(question);

		String[] tags = request.getParameterValues("tags");// 设置标签
		if (tags != null && tags.length > 0) {
			for (String tag : tags) {
				int tagId = Integer.parseInt(tag);
				QuestionTag questionTag = new QuestionTag();
				questionTag.setQuestionId(question.getId());
				questionTag.setTagId(tagId);
				questionTagService.saveOrUpdate(questionTag);
			}
		}

		return "redirect:index.do";
	}

	/**
	 * 获取所有标签
	 */
	@ResponseBody
	@RequestMapping("getTags.do")
	public List getTags(Model model) {
		Map<String, String> params = Maps.newHashMap();
		params.put("tagIshidden", "0");
		List<Tag> tags = tagService.getTags(params);
		List<Tag> newTags = Lists.newArrayList();
		for (Tag tag : tags) {
			Integer count = questionTagService.getTagCountByTagId(tag.getId());
			if (count != 0) {
				tag.setTagCount(count);
				newTags.add(tag);
			}
		}

		newTags = (List<Tag>) removeNullValue(newTags);
		return newTags;
	}

	/**
	 * 处理标签,将标签与问题一一对应
	 */
	private void setQuestionTag(List<Question> questions) {
		for (Question question : questions) {
			Map<String, String> qt = Maps.newHashMap();
			qt.put("questionId", question.getId() + "");
			List<QuestionTag> qts = questionTagService.getQuestionTags(qt);
			List<Tag> tags = new ArrayList<Tag>();
			for (QuestionTag questionTag : qts) {
				Tag tag = tagService.selectByPrimaryKey(questionTag.getTagId());
				tags.add(tag);
			}
			if (tags != null) {
				question.setTags(tags);
			}
			// 设置头像
			question.setUserImg(userService.selectByPrimaryKey(
					question.getUserId()).getAvatarImg());
		}
	}

	/**
	 * 同问
	 */
	@ResponseBody
	@RequestMapping("questionSame.do")
	public String questionSame(HttpServletRequest request) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			return "nologin";
		}

		Map<String, String> params = this.getParameters(request);
		int id = Integer.parseInt(params.get("id"));
		Question question = questionService.selectByPrimaryKey(id);

		Map<String, String> quparams = Maps.newHashMap();
		quparams.put("userId", user.getId() + "");
		quparams.put("questionId", question.getId() + "");
		quparams.put("hassame", "1");
		List<QuestionUser> questionUsers = questionUserService
				.getQuestionUsers(quparams);
		if (questionUsers.size() < 1) {
			question.setSameNum(question.getSameNum() + 1);
			questionService.saveOrUpdate(question);

			quparams.remove("hassame");
			QuestionUser questionUser = questionUserService.getQuestionUsers(
					quparams).get(0);
			questionUser.setHassame(1);
			questionUserService.saveOrUpdate(questionUser);
		}

		int sameNum = question.getSameNum();
		return "" + sameNum;
	}

	/**
	 * 投票
	 */
	@ResponseBody
	@RequestMapping("answerVote.do")
	public String answerVote(HttpServletRequest request) {
		User user = this.loginFrontUser(request);
		if (user == null) {
			return "nologin";
		}

		Map<String, String> params = this.getParameters(request);
		int aid = Integer.parseInt(params.get("aid"));
		Answer answer = answerService.selectByPrimaryKey(aid);

		Map<String, String> auparams = Maps.newHashMap();
		auparams.put("answerId", aid + "");
		auparams.put("userId", user.getId() + "");
		List<AnswerUser> aulist = answerUserService.getAnswerUsers(auparams);
		if (aulist.size() < 1) {
			answer.setVoteNum(answer.getVoteNum() + 1);
			answerService.saveOrUpdate(answer);

			AnswerUser answerUser = new AnswerUser();
			answerUser.setHasvote(1);
			answerUser.setUserId(user.getId());
			answerUser.setAnswerId(answer.getId());
			answerUserService.saveOrUpdate(answerUser);
		}

		int voteNum = answer.getVoteNum();
		return "" + voteNum;
	}

	@Deprecated
	@RequestMapping("getNoReadQuestions")
	public String getNoReadQuestions(HttpServletRequest request, Model model) {
		User user = this.loginFrontUser(request);
		List<Integer> quids = questionUserService
				.getUserhaveSeeQuestionIds(user.getId());
		if (quids.size() > 0) {
			Map<String, Object> params = Maps.newHashMap();
			params.put("0", "hidden");
			params.put("ids", quids);
			List<Question> questions = questionService
					.getUnreadQuestions(params);

			setQuestionTag(questions);
			model.addAttribute("questions", questions);
			model.addAttribute("msg",
					user.getUserAccount() + ", " + questions.size() + "个未读个问题");
		} else {
			Map<String, String> params = Maps.newHashMap();
			params.put("0", "hidden");// 未被设置隐藏

			List<Question> questions = questionService.getQuestions(params);
			setQuestionTag(questions);
			model.addAttribute("questions", questions);
		}

		return "discussion/discussion";
	}

	/**
	 * 获得全部回复多和得票多的用户
	 */
	@ResponseBody
	@RequestMapping("getTopUser.do")
	public List getTopUser() {
		Map<String, String> params = Maps.newHashMap();
		List<Map> newList = Lists.newArrayList();
		List<Map> list = answerService.getTopUser(params);

		// 设置头像
		for (Map map : list) {
			map.put("userImg", userService.selectByPrimaryKey((Integer) map.get("userId")) .getAvatarImg());
			newList.add(map);
		}

		return newList;
	}

	/**
	 * 获得上月回复多和得票多的用户
	 */
	@ResponseBody
	@RequestMapping("getTopUserMonth.do")
	public List getTopUserMonth() {
		Map<String, String> params = Maps.newHashMap();
		params.put("beginTime", DateUtils.findLastMonth()[0]);
		params.put("endTime", DateUtils.findLastMonth()[1]);
		List<Map> list = answerService.getTopUser(params);

		List<Map> newList = Lists.newArrayList();
		// 设置头像
		for (Map map : list) {
			map.put("userImg", userService.selectByPrimaryKey((Integer) map.get("userId")) .getAvatarImg());
			newList.add(map);
		}

		return newList;
	}

	/**
	 * 提问是否需要审核
	 */
	private boolean isQuestionAudit() {
		boolean flag = false;
		try {
			DiscussionConfig config = configService.getValue(QUESTION_AUDIT);
			if (config.getDisValue().equalsIgnoreCase("YES")) {
				flag = true;
			}
		} catch (Exception e) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 回答是否需要审核
	 */
	private boolean isAnswerAudit() {
		boolean flag = false;
		try {
			DiscussionConfig config = configService.getValue(ANSWER_AUDIT);
			if (config.getDisValue().equalsIgnoreCase("YES")) {
				flag = true;
			}
		} catch (Exception e) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 去除集合中null
	 */
	private static Collection<?> removeNullValue(Collection<?> collection) {
		if (collection == null) {
			return null;
		}
		Iterator<?> iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object o = iterator.next();
			if (null == o || "".equals(o + "")) {
				iterator.remove();
			}
		}
		return collection;
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
	private DiscussionConfigService configService;

	@Autowired
	private UserService userService;
}
