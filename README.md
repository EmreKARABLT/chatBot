# Project 2-2 Group 4
Gabrijel Radovcic
## Chat Bot
# Getting Started

### Open CV
To run the project you'll need to install open cv and add it as a module to this project.
Here is a link on how to do that
https://medium.com/@aadimator/how-to-set-up-opencv-in-intellij-idea-6eb103c1d45c

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

Within Logic there is the subpackage `CFG` This contains the logic for the cfg parser
- The `GrammarEditor` class is used for editing the grammar file
- The `RecursiveParser` class is used for interpreting and parsing language using Context Free Grammar
- The `Response` class is a data class used in recursive parser

Withing Logic there is the subpackage `Face` This contains the logic for face detection
- The `FaceDetection` class is an abstract class used to define face detection classifiers
- The `Haar` class is a face classifiers using a Haar model
- The `CNN` class is a face classifiers using a convolutional neural network for face recognition
- The `Model` class is used for running tensorflow models

The `view` package contains all the frontend ui for the user assistant.
- The `MainInterface` class merges all the inteface panels to create a UI to run the application
- The `MenuPane` class is the starting point for the application and shows a simple UI which allows the user to select what they want to do
- The `AssistantPane` class contains all the UI code for the assistant panel in the user interface
- The `TemplateSelectorPane` class contains all the UI code for the selection of a skill to edit in the user interface
- The `TemplateBuilderPane` class contains all the UI code for the creation/deletion and editing for the skills in the user interface

