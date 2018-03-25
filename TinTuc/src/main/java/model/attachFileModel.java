package model;

public class attachFileModel {
	private int id;
	private int post_id;
	private String name;
	private String link;
	public attachFileModel(String name,String link) {
		this.name=name;
		this.link = link;
	}
	public attachFileModel() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	
}
