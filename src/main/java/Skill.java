public interface Skill {
	default Boolean isQuestion(String question){
		return false;
	}
	default String getResponse(String question) {
		return null;
	}

}
