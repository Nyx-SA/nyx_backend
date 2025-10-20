To run the project, create under the nyx-app folder a .env file with the next enviroment variables:

DB_URL=jdbc:postgresql://localhost:"PORT"/"DATABASE_NAME"  
DB_USER="YOUR_POSTGRESQL_USER"  
DB_PASS="YOUR_POSTGRESQL_PASSWORD"  

JWT_SECRET_KEY="KEY"

If you want to test the password reset through email functionality you will have to create or use your own gmail account and add the variables here:

MAIL_USERNAME= "YOUR_EMAIL"
MAIL_PASSWORD= "YOUR_APP_PASSWORD" (If you dont have one you can generate yours under Security tab on your gmail account)

  
I recommend creating a new postgresql user and assinging the database you're going to use under that user.
