# Hangman Web (Angular) + Spring Boot App

## Build
- Clone the web app repo: git clone git@github.com:michelazevedo/hangman-web.git
- Clone the spring boot app repo: git clone git@github.com:michelazevedo/hangman.git
- Enter the web project directory (hangman-web) and run: npm install
- Compile the source: ng build --prod (assuming npm is installed)
- Copy the resources from hangman-web\dist\hangman-web to the directory hangman\src\main\resources\static
- In the spring boot app directory (hangman) run: mvnw clean package

## Running

- Start the project running from the "hangman" directory: java -jar target/hangman.jar 
- Go to http://localhost:8080/ 

## Game operation
- The game will pick a ramdom word for you;
- You should pick a word from the "Word buttons painel";
- Try to guess the word's characters. You have 6 changes to guess the word;
- The game ends and restarts if you run out of attempts or if you guess the word correctly.
