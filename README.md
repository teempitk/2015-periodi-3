# tiralabra

Toteutan projektissa tiedostoja pakkaavan ohjelman. Tällä erää pakkaus pohjautuu pelkästään Huffman-koodaukseen, eikä ole mainittavan tehokas muihin kuin tekstitiedostoihin.

Pääohjelma Main/Main.java on asetettu tällä hetkellä pakkaamaan/purkamaan pelkästään sampleFiles-kansion testitiedostoja. Sopivat rivit pois kommentoimalla ajamista voi kokeilla tietenkin myös muille tiedostoille. Pakkaus ja purku onnistuvat käskyillä:

java Main c pakattavaTiedosto pakatunTiedostonNimi
java Main d purettavaTiedosto puretunTiedostonNimi

Lähdekoodi on jaettu paketteihin seuraavasti:

Main: Pääohjelma
Utils: Yleishyödyllisiä metodeita (lähinnä bittien muunnoksia merkkijonoiksi ja päin vastoin)
Huffman: Huffman-koodauksen osat
DataStructures: Koodauksessa käytettäviä tietorakenteita.
