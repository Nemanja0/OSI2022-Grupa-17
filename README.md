**Projektni zadatak:** 
Softver za prodaju ulaznica

Opis zadatka:

-Implementirati jednostavan sistem za prodaju ulaznica. Sistem koriste različite vrste korisnika, a svi moraju imati otvoren nalog. Svi podaci o registrovanim korisnicima se čuvaju u datoteci u sklopu aplikacije. Za korisnički nalog obavezno se čuvaju ime i prezime korisnika, korisničko ime i lozinka. Moguće je dodati i druge podatke. Registracija na sistem je otvorena samo za kreiranje običnih korisničkih naloga. Kada se prijave na sistem, korisnici mogu koristiti različite opcije, u zavisnosti od vrste naloga koje imaju. Potrebno je realizovati tri vrste korisničkih naloga: korisnički, administratorski i klijentski.

-Administratorske i klijentske naloge mogu kreirati samo administratori. Prvi podrazumijevani nalog dolazi u sklopu instalacije i nakon prve prijave na sistem sa tim (admin/admin) nalogom mora se promijeniti šifra. Administratori imaju mogućnost upravljanja klijentskim i korisničkim nalozima (aktivacija, suspendovanje, brisanje, poništavanje šifre, …) i blokiranja klijentskih događaja.

-Klijentski nalozi mogu kreirati događaje za koje se prodaju ulaznice. Svaka ulaznica ima jedinstvenu šifru na nivou cijelog sistema, naziv događaja, datum, vrijeme, iznos, ime korisnika koji je kupio kartu ako je to obavezan parametar i ostale bitne detalje. Prilikom kreiranja događaja unose se svi potrebni podaci (naziv, datum, vrijeme, cijena ulaznice, da li se kupuje na ime ili ne…). Dodati sve potrebne detalje. Klijenti mogu pregledati prodate ulaznice za svaki događaj, dobiti izvještaje o prodaji u određenom periodu (po datumu održavanja događaja) u txt formatu, poništavanja pojedinačnih ulaznica… Sve ulaznice se čuvaju kao txt fajlovi.

-Korisnici imaju mogućnost da pregledaju događaje, pročitaju opis i sve informacije, kupe ulaznicu ili ponište prethodno kupljenu ulaznicu. Prilikom kupovine se unose podaci koji su potrebni za plaćanje i način preuzimanja ulaznice (na lokaciji ili elektronski). Korisnici na svom nalogu imaju ukupan iznos kredita sa kojim obavljaju kupovinu (smatrati da se kupovina kredita ne obavlja u ovom sistemu).

-Svi direktorijumi i fajlovi koje sistem koristi treba da budu u nekom folderu na računaru, čija se putanja čuva u konfiguracionom fajlu, uz sve druge potrebne parametre.

-Svaki korisnik sistem dužan je da mijenja lozinku za pristup nakon svakih n prijava na sistem, pri čemu se n definiše u konfiguraciji na nivou cijelog sistema.
