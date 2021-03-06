# Hangman Web (Angular) + Spring Boot App

## Repositories
- Frontend: https://github.com/michelazevedo/hangman-web
- Backend: https://github.com/michelazevedo/hangman

## Build
- Clone the web app (frontend) repo: git clone git@github.com:michelazevedo/hangman-web.git
- Clone the spring boot app (backend) repo: git clone git@github.com:michelazevedo/hangman.git
- Enter the web app project directory (hangman-web) and run: npm install
- Compile the source: ng build --prod (assuming npm is installed)
- Copy the resources from hangman-web\dist\hangman-web to the directory hangman\src\main\resources\static
- In the spring boot app directory (hangman) run: mvnw clean package

## Running

- Start the project running from the "hangman" directory: java -jar target/hangman.jar 
- Go to http://localhost:8080/ 

## Game operation
- The game will pick a random word for you;
- You should pick a char from the "Buttons Painel";
- Try to guess the word's characters. You have 6 tries to guess the word;
- The game ends and restarts if you run out of attempts or if you guess the word correctly.
