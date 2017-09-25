package com.rbao.east.entity;


public class Attach extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME_CONTENT = "rb_content";
	public static final String TABLE_NAME_BORROW="rb_borrow";

	private Integer id;

    private String attachTitle;

    private String attachRealTitle;

    private String attachFileType;

    private Integer attachSequence;

    private String attachPath;

    private String attachTablename;

    private Integer attachRelateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttachTitle() {
        return attachTitle;
    }

    public void setAttachTitle(String attachTitle) {
        this.attachTitle = attachTitle == null ? null : attachTitle.trim();
    }

    public String getAttachRealTitle() {
        return attachRealTitle;
    }

    public void setAttachRealTitle(String attachRealTitle) {
        this.attachRealTitle = attachRealTitle == null ? null : attachRealTitle.trim();
    }

    public String getAttachFileType() {
        return attachFileType;
    }

    public void setAttachFileType(String attachFileType) {
        this.attachFileType = attachFileType == null ? null : attachFileType.trim();
    }

    public Integer getAttachSequence() {
        return attachSequence;
    }

    public void setAttachSequence(Integer attachSequence) {
        this.attachSequence = attachSequence;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath == null ? null : attachPath.trim();
    }

    public String getAttachTablename() {
        return attachTablename;
    }

    public void setAttachTablename(String attachTablename) {
        this.attachTablename = attachTablename == null ? null : attachTablename.trim();
    }

    public Integer getAttachRelateId() {
        return attachRelateId;
    }

    public void setAttachRelateId(Integer attachRelateId) {
        this.attachRelateId = attachRelateId;
    }
}