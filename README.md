# tiralabra

Toteutin tietorakenteiden ja algoritmien harjoitustyönä pakkausohjelman. Projektin jar-tiedosto löytyy tiralabra/dist -hakemistosta, ja sen ajaminen onnistuu seuraavilla käskyillä:

> java -jar tiralabra.jar -c pakattavaTiedosto

> java -jar tiralabra.jar -d purettavaTiedosto(.teemuzip)

Parametri -c tarkoittaa pakkaamista (compress) ja -d purkua (decompress). Lisäksi ohjelmalle voi antaa parametrin -p, jolloin tulostuu tietoa suorituksen etenemisestä ja suoritusajasta. Ohjelman pakkauspino on kolmitasoinen: 1. Burrows-Wheeler -muunnos 2. Move to Front -muunnos 3. Huffman-koodaus. Kaikki käytetyt menetelmät ovat myös ihan ”oikeassa” käytössä, ja nämä kolme esiintyvät mm. osana bzip2:n kompressiopinoa.

Lähdekoodi löytyy hakemistosta tiralabra/src. Koodi on jaettu pakkauksiin seuraavasti:

Main: Pääohjelma

Utils: Yleishyödyllisiä metodeita (lähinnä bittien muunnoksia merkkijonoiksi ja päin vastoin)

Huffman: Huffman-koodauksen osat

DataStructures: Koodauksessa käytettäviä tietorakenteita.

Transforms: MTF- ja BWT-muunnokset.

IO: Bittitason tiedoston lukuun ja kirjoitukseen käytettäviä luokkia.
