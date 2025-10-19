To run the project, create under the nyx-app folder a .env file with the next enviroment variables:

DB_URL=jdbc:postgresql://localhost:"PORT"/"DATABASE_NAME"  
DB_USER="YOUR_POSTGRESQL_USER"  
DB_PASS="YOUR_POSTGRESQL_PASSWORD"  

JWT_SECRET_KEY="KEY"

  
I recommend creating a new postgresql user and assinging the database you're going to use under that user.
