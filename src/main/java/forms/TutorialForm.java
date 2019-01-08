
package forms;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class TutorialForm {

	private int		id;
	private int		citizenId;
	private int		tutorialId;
	private String	title;
	private String	text;
	private String	pictures;
	private int		number;


	public TutorialForm() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getCitizenId() {
		return this.citizenId;
	}

	public void setCitizenId(final int citizenId) {
		this.citizenId = citizenId;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public String getPictures() {
		return this.pictures;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	@Min(0)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public int getTutorialId() {
		return this.tutorialId;

	}

	public void setTutorialId(int tutorialId) {
		this.tutorialId = tutorialId;

	}

}
