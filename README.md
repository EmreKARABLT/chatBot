# Project 2-2 Group 4
## Chat Bot
# Getting Started

### Run Application
Run by executing the main class in 'src/main/java/view/MainInterface.java'



## File guide

### main module
The `Logic` package contains all the backend for the user assistant.
- The `Action` class is a simple data class for storing actions
- The `LevenshteinDistance` class is used for computing Levenshtein distance for typos
- The `SkillParser` class is used to parse skills from a text file
- The `SkillTemplate` class is a data class used for managing skills
- The `TemplateController` class contains the logic for matching skills to questions
- The `TextProcessor` class is used for proccesing text to the correct format for being interpreted
- The `Vocabulary` class creates a vocabulary for each skill template

The `view` package contains all the frontend ui for the user assistant.
- The `MainInterface` class merges all the inteface panels to create a UI to run the application
- The `MenuPane` class is the starting point for the application and shows a simple UI which allows the user to select what they want to do
- The `AssistantPane` class contains all the UI code for the assistant panel in the user interface
- The `TemplateSelectorPane` class contains all the UI code for the selection of a skill to edit in the user interface
- The `TemplateBuilderPane` class contains all the UI code for the creation/deletion and editing for the skills in the user interface

