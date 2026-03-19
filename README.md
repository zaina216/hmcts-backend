# HMCTS Dev Test Backend
You should be able to run `./gradlew build` to start with to ensure it builds successfully. Then from that you
can run the service in IntelliJ (or your IDE of choice) or however you normally would.

To run the PostgreSQL database, please run this command
docker run --name hmcts-postgres   -e POSTGRES_USER=postgres   -e POSTGRES_PASSWORD=postgres   -e POSTGRES_DB=hmcts   -p 5432:5432   -d postgres
if you have issues connecting to the database, this might help
sudo docker exec -it hmcts-postgres psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE hmcts TO postgres;"
