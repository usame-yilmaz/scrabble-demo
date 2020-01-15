## Swagger link
swagger ile servisler çağrılabilir, link:
http://localhost:8080/swagger-ui.html#/

h2 database console:
http://localhost:8080/h2-console
jdbc url: jdbc:h2:file./data/db

## Örnek requestler:

Create board
curl -X POST "http://localhost:8099/game/createBoard"

Activate board
curl -X POST "http://localhost:8080/game/setStatus?boardId=1&status=ACTIVE" -H "accept: */*"

Play move
http://localhost:8099/game/play?boardId=1&moves=a,4,4&moves=ğ,5,4&moves=a,6,4&moves=ç,7,4
http://localhost:8099/game/play?boardId=1&moves=t,4,5&moves=k,4,6&moves=ı,4,7

Get Board Content
curl -X GET "http://localhost:8080/game/getBoardContent?boardId=1&sequence=1"


## Compilation Command
- `mvn clean install`
