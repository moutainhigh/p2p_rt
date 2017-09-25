package com.rbao.east.utils;

import java.util.List;

import com.rbao.east.entity.TreeModel;



public class TreeUtils {
	
	public static String getCallBackTreeString(int pId,int id,List<TreeModel> tg){
		String tree="";
		 if(pId==id){
	            tree = "<ul class='tree treeFolder collapse'>";
	        }else{
	            tree = "<ul >";
	       }
		 for(int i=0;i<tg.size();i++)
	        {
	        	TreeModel tp =(TreeModel)tg.get(i);
		         if(tp.getPid() == pId){
			            tree += "<li  curId="+tp.getId()+"><a href=\"javascript:void(0)\" onclick=\"$.bringBack({id:'"+tp.getId()+"', districtName:'"+tp.getTitle()+"'})\">"+tp.getTitle()+"</a>";
		                tree += getCallBackTreeString(tp.getId(),id,tg);
		                tree += "</li>";
		            }
	        }
		return tree+"</ul>";
	}
	
	public static String getCallBackTreeStrings(int pId,int id,List<TreeModel> tg){
		String tree = "<ul >";
		 for(int i=0;i<tg.size();i++)
	        {
	        	TreeModel tp =(TreeModel)tg.get(i);
		         if(tp.getPid() == pId){
			            tree += "<li curId="+tp.getId()+"><a href=\"javascript:void(0)\" onclick=\"$.bringBack({id:'"+tp.getId()+"', districtName:'"+tp.getTitle()+"'})\">"+tp.getTitle()+"</a>";
		                tree += getCallBackTreeString(tp.getId(),id,tg);
		                tree += "</li>";
		            }
	        }
		return tree+"</ul>";
	}
	public static String getCallBackMoreTreeStrings(int pId,int id,List<TreeModel> tg){
		String tree = "<ul >";
		 for(int i=0;i<tg.size();i++){
	        	TreeModel tp =(TreeModel)tg.get(i);
		         if(tp.getPid() == pId){
			            tree += "<li><a><input type=\"checkbox\" group=\"orgId\" />"+tp.getTitle()+"</a>";
		                tree += getCallBackMoreTreeString(tp.getId(),id,tg);
		                tree += "</li>";
		            }
	        }
		return tree+"</ul>";
	}
	public static String getCallBackMoreTreeString(int pId,int id,List<TreeModel> tg){
		String tree="";
		 if(pId==id){
	            tree = "<ul class='tree treeFolder collapse'>";
	        }else{
	            tree = "<ul >";
	       }
		 for(int i=0;i<tg.size();i++)
	        {
	        	TreeModel tp =(TreeModel)tg.get(i);
		         if(tp.getPid() == pId){
			            tree += "<li><input style=\"margin-left: 90px;\" type=\"checkbox\" value=\"{id:'"+tp.getId()+"', districtName:'"+tp.getTitle()+"'}\" name=\"orgId\"/>"+tp.getTitle()+"";
		                tree += getCallBackMoreTreeString(tp.getId(),id,tg);
		                tree += "</li>";
		            }
	        }
		return tree+"</ul>";
	}
	public static String getTreeModelString(String url,int pId,int id,List<TreeModel> tg){
		String tree="";
		 if(pId==id){
	            tree = "<ul class='tree treeFolder'>";
	        }else{
	            tree = "<ul >";
	       }
		 for(int i=0;i<tg.size();i++)
	        {
	        	TreeModel tp =(TreeModel)tg.get(i);
	        	if(tp.getPid() == pId){
	        			tree += "<li><a href="+url+tp.getId()+" target=\"ajax\" rel=\"jbsxBox\" >"+tp.getTitle()+"</a>";
	        			tree += getTreeModelString(url,tp.getId(),id,tg);
	        			tree += "</li>";
		            }
	        }
		return tree+"</ul>";
	}
	
	

	public static String getTreeModelStrings(String url,String target,int pId,int id,List<TreeModel> tg){
		String tree="<ul >";
		 for(int i=0;i<tg.size();i++)
	        {
	        	TreeModel tp =(TreeModel)tg.get(i);
	        	if(tp.getPid() == pId){
	        			tree += "<li><a href="+url+tp.getId()+" target=\"ajax\" rel="+target+" >"+tp.getTitle()+"</a>";
	        			tree += getTreeModelStrings(url,target,tp.getId(),id,tg);
	        			tree += "</li>";
		            }
	        }
		return tree+"</ul>";
	}
	
	
	
	public static String getTreeStringNoCheckBox(int pId,int id,List<TreeModel> tg,String clickString){
        String tree="";
        if(pId==id){
            tree = "<ul class='tree expand'>";
        }else{
            tree = "<ul >";
        }
        for(int i=0;i<tg.size();i++)
        {
        	TreeModel tp =(TreeModel)tg.get(i);
	         if(tp.getPid() == pId){
		            tree += "<li><a href=\"javascript:void(0)\" onmouseup="+clickString+"("+tp.getId()+",'"+tp.getTitle()+"') >"+tp.getTitle()+"</a>";
	                tree += getTreeStringNoCheckBox(tp.getId(),id,tg,clickString);
	                tree += "</li>";
	            }
        }
        return tree+"</ul>";
    } 
	
	public static String getTreeStringWidthCheckBox(int pId,int id,List<TreeModel> treeList,List<TreeModel> havaeNode,String checkboxName){
        String tree="";
        if(pId==id){
            tree = "<ul class='tree treeFolder treeCheck expand'>";
        }else{
            tree = "<ul >";
        }
        for(int i=0;i<treeList.size();i++)
        {
        	TreeModel tp =(TreeModel)treeList.get(i);
	         if(tp.getPid() == pId){
	        		if(isExit(tp.getId(),havaeNode)){
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+" checked>"+tp.getTitle()+"</a>";  
	        		}else{
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+" >"+tp.getTitle()+"</a>";  
	        		}
	                tree += getTreeStringWidthCheckBox(tp.getId(),id,treeList,havaeNode,checkboxName);
	                tree += "</li>";
	            }
        }
        return tree+"</ul>";
    } 
	
	
	public static String getTreeStringWidthCheckBoxOne(List<TreeModel> treeList,List<TreeModel> havaeNode,String checkboxName){
        String tree = "<ul class='tree treeFolder treeCheck expand'>";
        for(int i=0;i<treeList.size();i++)
        {
        	TreeModel tp =(TreeModel)treeList.get(i);
	        		if(isExit(tp.getId(),havaeNode)){
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+" checked>"+tp.getTitle()+"</a>";  
	        		}else{
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+" >"+tp.getTitle()+"</a>";  
	        		}
	        		tree += "<ul ></ul>";
	                tree += "</li>";
        }
        return tree+"</ul>";
    } 
	

	public static String getTreeStringWidthTreeModelList(List<TreeModel> treeList,String checkboxName){
        String tree = "<ul class='tree treeFolder expand'>";
        for(int i=0;i<treeList.size();i++)
        {
        	TreeModel tp =(TreeModel)treeList.get(i);
	        		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+" >"+tp.getTitle()+"</a>";  
	        		tree += "<ul ></ul>";
	                tree += "</li>";
        }
        return tree+"</ul>";
    } 
	 
	public static String getTreeString(int pId,List<TreeModel> firstTree,List<TreeModel> firseTreeChecked,
			List<TreeModel> secondTree,List<TreeModel> thirdTree,List<TreeModel> haveNode,String checkboxName,
			List<TreeModel> noParentTree,List<TreeModel> noParentTreeChecked){
        String tree="";
        if(pId==0){
            tree+= "<ul class='tree treeFolder treeCheck expand'>";
      }else{
            tree = "<ul>";
       }
        for(int i=0;i<firstTree.size();i++)
        {	
        	TreeModel tp =(TreeModel)firstTree.get(i);
        	if(tp.getPid() == pId){
        		if(isExit(tp.getId(), firseTreeChecked)){
                    tree += "<li><a tname=\"group\" tvalue="+tp.getId()+" checked>"+tp.getTitle()+"</a>";   
        		}else{
                    tree += "<li><a tname=\"group\" tvalue="+tp.getId()+">"+tp.getTitle()+"</a>";   
        		}
//                GetTreeSecondString(tree, pId, role, groupRole);
                tree+="<ul>";
            	tree+=GetTreeSecondString(tp.getId(),secondTree,thirdTree,haveNode,checkboxName,tp.getId());
                tree+="</ul>";
                tree += "</li>";
        	}
        }
        return tree+"</ul>";
    } 
	
	
	
	public static String GetTreeSecondString(int pId,List<TreeModel> secondTree,List<TreeModel> thirdTree,List<TreeModel> haveNode,String checkboxName,int groupId)
	{
		String tree="";
		if(secondTree!=null){
			for(int i=0;i<secondTree.size();i++)
	        {	

				TreeModel tp1 =(TreeModel)secondTree.get(i);
	        	if(tp1.getPid() == pId){
	        		if(isExitInGroup(tp1.getId(),haveNode,pId)){
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp1.getId()+"_"+groupId+" checked=\"true\">"+tp1.getTitle()+"</a>";  
	        		}else{
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp1.getId()+"_"+groupId+" >"+tp1.getTitle()+"</a>";  
	        		}
	                tree+="<ul>";
	        		tree+=GetTreeThirdString(tp1.getId(),thirdTree,haveNode,checkboxName,groupId);
	                tree+="</ul>";
	        		tree += "</li>";
	        	}
	        }
		}
		return tree;
	}
	
	public static String GetTreeThirdString(int pId,List<TreeModel> thirdTree,List<TreeModel> haveNode,String checkboxName,int groupId)
	{
		String tree="";
		if(thirdTree!=null){
			for(int i=0;i<thirdTree.size();i++)
	        {
				TreeModel tp =(TreeModel)thirdTree.get(i);
	        	if(tp.getPid() == pId){
	        		if(isExit(tp.getPid(),haveNode)){
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+"_"+groupId+" checked=\"true\">"+tp.getTitle()+"</a>";  
	        		}else{
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+"_"+groupId+" >"+tp.getTitle()+"</a>";  
	        		}
	                tree+="<ul>";
	                tree += GetTreeThirdString(tp.getId(),thirdTree,haveNode,checkboxName,groupId);
	                tree+="</ul>";
	                tree += "</li>";
	       	}
	        }
		}
		return tree;
	}
	
	public static boolean isExit(int id,List<TreeModel> treeList){
		boolean flag=false;
		if(treeList!=null&&treeList.size()>0){
			for(int i=0;i<treeList.size();i++){
				if(id==treeList.get(i).getId()){
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	
	public static boolean isExitInGroup(int id,List<TreeModel> treeList,int GroupId){
		boolean flag=false;
		if(treeList!=null&&treeList.size()>0){
			for(int i=0;i<treeList.size();i++){
				if(id==treeList.get(i).getId()&&treeList.get(i).getGid()==GroupId){
					flag=true;
					break;
				}
			}
		}
		return flag;
	}
	/**
	 * 所有前台用户
	 * @param treeList
	 * @param checkboxName
	 * @return
	 */
	public static String getBorrowedUser(List<TreeModel> treeList){
		
        String tree = "<ul class='tree treeFolder expand'>";
        for(int i=0;i<treeList.size();i++)
        {
        	TreeModel tp =(TreeModel)treeList.get(i);
	            		tree += "<li><a  href=\"javascript:void(0)\" onclick=\"backValue('"+tp.getId()+"','"+tp.getTitle()+"')\" tvalue="+tp.getId()+" >"+tp.getTitle()+"</a>";  
	        		tree += "<ul ></ul>";
	                tree += "</li>";
        }
        return tree+"</ul>";
    } 
	/**
	 * 所有前台用户
	 * @param treeList
	 * @param checkboxName
	 * @return
	 */
	public static String getTreeStringWidthALlFrontUser(List<TreeModel> treeList,String checkboxName){
		
        String tree = "<ul class='tree treeFolder treeCheck expand'>";
        for(int i=0;i<treeList.size();i++)
        {
        	TreeModel tp =(TreeModel)treeList.get(i);
	            		tree += "<li><a  tname="+checkboxName+" tvalue="+tp.getId()+" >"+tp.getTitle()+"</a>";  
	        		tree += "<ul ></ul>";
	                tree += "</li>";
        }
        return tree+"</ul>";
    } 
	/**
	 * 前台页面内容列表
	 */
	public static String getMenu(int pId,int id,List<TreeModel> tg){
		 String tree="";
		/* if(pId==id){
	            tree = "<dd style=\"display: block\">";
	        }else{
	            tree = "<dd >";
	       }*/
		 for(int i=0;i<tg.size();i++)
	        {
	        	TreeModel tp =(TreeModel)tg.get(i);
	        	
	        	
		         if(tp.getPid() == pId){
		        	 
		        	 String url = tp.getUrl();
		        	 //<img width=\"5\" height=\"7\" src=\"../../../common/front/images/ny/list_03.jpg\">
        		  tree += "<a url=\""+url+"\" curId="+tp.getId()+">"+tp.getTitle()+"</a>";
	              tree += getMenu(tp.getId(),id,tg);
	            }
	        }
		return tree;
	}
	
	public static String getMenuParent(int pId,int id,List<TreeModel> tg,Integer channelIdParent){
		String tree = "";
		 for(int i=0;i<tg.size();i++)
	        {
	        	TreeModel tp =(TreeModel)tg.get(i);
		         if(tp.getPid() == pId){
		        	
		        	 String url = tp.getUrl();
		        	 /*<dl>
		             <dd><a href="#">关于我们</a></dd>
		             <dt><img width="3" height="5" src="images/ny/list_03.jpg"> 团队介绍</dt>
		              <dt><img width="3" height="5" src="images/ny/list_03.jpg"> 合作伙伴</dt>
		               <dt><img width="3" height="5" src="images/ny/list_03.jpg"> 专家顾问</dt>
		                <dt><img width="3" height="5" src="images/ny/list_03.jpg"> 官方公告</dt>
		                 <dt><img width="3" height="5" src="images/ny/list_03.jpg"> 诚聘英才</dt>
		             </dl>*/
		        	 if(channelIdParent!=0&& tp.getId()==channelIdParent){
		        		 	tree +="<dl>";
				            tree += "<dd class=\"active\"><b></b><a url=\""+url+"\" curId="+tp.getId()+">"+tp.getTitle()+"</a></dd>";
				            tree +="<dt style=\"display: block\">";
			                tree += getMenu(tp.getId(),id,tg);
			                tree += "</dt></dl>";
		        	 }else{
		        		    tree +="<dl >";
				            tree += "<dd><b></b><a url=\""+url+"\" curId="+tp.getId()+">"+tp.getTitle()+"</a></dd>";
				            tree +="<dt>";
			                tree += getMenu(tp.getId(),id,tg);
			                tree += "</dt></dl>";
		        	 }
		            }
	        }
		return tree;
	}
}
