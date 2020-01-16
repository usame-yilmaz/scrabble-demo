## Swagger link
swagger ile servisler çağrılabilir, uygulama ayağa kalktığında otomatik olarak açılacaktır. link:
http://localhost:8080/swagger-ui.html#/

### h2 database console:
http://localhost:8080/h2-console
jdbc url: jdbc:h2:file./data/db

## Örnek requestler:

### Create board
`curl -X POST "http://localhost:8080/game/createBoard"`

### Activate board
`curl -X POST "http://localhost:8080/game/setStatus?boardId=1&status=ACTIVE" -H "accept: */*"`

### Play move
`http://localhost:8080/game/play?boardId=1&moves=a,4,4&moves=ğ,5,4&moves=a,6,4&moves=ç,7,4`
`http://localhost:8080/game/play?boardId=1&moves=t,4,5&moves=k,4,6&moves=ı,4,7`

### Get Board Content(see content as html)
`curl -X GET "http://localhost:8080/game/getBoardContent?boardId=1&sequence=1"`

örnek response:
`***************<br>***baba********<br>****r**********<br>****a**********<br>****b**********<br>****a**********<br>****c**********<br>****ı**********<br>***************<br>***************<br>***************<br>***************<br>***************<br>`


response as html:
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
|-------------------------------|
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
|\*\*\*baba\*\*\*\*\*\*\*\*|
|\*\*\*\*r\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*a\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*b\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*a\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*c\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*ı\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
|\*\*\*\*\*\*\*\*\*\*\*\*\*\*\*|
  

### Get Words
`curl -X GET "http://localhost:8080/game/getWords?boardId=1"`

örnek response:
`[
  "Word: baba Points:8",
  "Word: arabacı Points:13",
  "Word: araba Points:7"
]`

## Compilation Command
- `mvn clean install`
