# Buraya dokumantasyon gelecek

Örnek request:


http://localhost:8099/game/createBoard?boardId=1

http://localhost:8099/game/play?boardId=1&moves=a,4,4&moves=ğ,5,4&moves=a,6,4&moves=ç,7,4
http://localhost:8099/game/play?boardId=1&moves=t,4,5&moves=k,4,6&moves=ı,4,7


Runtime Exception değişmeli

CREATE BOARD:
	- GAME içindeki oyun listesine yeni oyun eklenir(DB de olabilir)
	- Pasif statüde eklenir. SetStatus servisi ile aktiflenmesi gerekir.
	- ID dönülür

PLAY:
	IN: array MOVE

	- Board objesindeki statü alanı aktif olmalı
	- Tahta boşsa herhangi bir yere ekleyebilir
	- Sıralı olmalı, sağdan sola veya aşağıya
	- Tekrar etmemeli
	- En az 1 harfin çevresi dolu olmalı
	- Hiçbiri üstüste gelmemeli
	- Ekledikten sonra board üzerinde anlamsız kelime kalmamalı(KELİME BULMA), öyleyse hamle iptal
	- Hücrelere harfler yerleşmeli
	- Yeni eklenen kelimeler tespit edilip puan hesabı-log vs yapılmalı
	- Başarılı move sonrası
		- Kelimeler board'a kaydedilmeli (db ya da memory)
		- Order(El sayısı) ile birlikte board kaydedilmeli

KELIME BULMA ve KONTROL:
	- Sadece eklenen x ve y ekseninin tamamına bakılmalı
	- Eklenen kelimeler sözlükte yoksa hata vermeli
	
PUAN MAP:

DICTIONARY SET:	

GET WORDS:
	- Board objesindeki "words" listesi çekilir. DB persist???

GET BOARD CONTENT:
	- MOVE servisinde kaydedilen bilgilere göre getirilir.

SET STATUS:
	- Deactivated true olan tahta yeniden aktif olamaz.
	- boardId ve Status bilgisi güncellenir.


GAME
	(list) boards
	(set) dictionary
BOARD
	(enum) status
	(bool) deactivated, initial false
	(int) order -- kaçıncı el olduğu bilgisi, initial 1
	(15x15 array) cells
	(tile array) tiles --- initial Değerler tablodan alınabilir???
 	(List Word) words
CELL
	(bool) occupied --initial false
	(tile) tile	-- initial null
MOVE
	tile
	(int) row
	(int) column
TILE
	id
	(char) letter
WORD
	(list tile) tiles
	(int) startRow
	(int) startCol
	(Position Enum) Horizontal, Vertical
	(int) point -- or calculatePoint methodu ile

## Description
This Project shows the list of Users which are stored in the In-Memory H2 Database. Using the following endpoints, different operations can be achieved:
- `/users` - This returns the list of Users in the Users table which is created in H2
- `/users/name/{name}` - This returns the details of the Users passed in URL
- `/users/load` - Add new users using the Users model. eg. { "name": "Ajay", "teamName": "Development", "salary": 100 }

## Libraries used
- Spring Boot
- Spring Configuration
- Spring REST Controller
- Spring JPA
- H2
- Development Tools

## Compilation Command
- `mvn clean install`
