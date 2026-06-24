package br.ufscar.pooa.cinema_api.infrastructure.database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.pooa.cinema_api.domain.entities.Address;
import br.ufscar.pooa.cinema_api.domain.entities.Client;
import br.ufscar.pooa.cinema_api.domain.entities.Genre;
import br.ufscar.pooa.cinema_api.domain.entities.Manager;
import br.ufscar.pooa.cinema_api.domain.entities.Movie;
import br.ufscar.pooa.cinema_api.domain.entities.Room;
import br.ufscar.pooa.cinema_api.domain.entities.Row;
import br.ufscar.pooa.cinema_api.domain.entities.Seat;
import br.ufscar.pooa.cinema_api.domain.entities.Session;
import br.ufscar.pooa.cinema_api.domain.entities.Theater;
import br.ufscar.pooa.cinema_api.domain.enums.AgeRating;
import br.ufscar.pooa.cinema_api.domain.enums.Format;
import br.ufscar.pooa.cinema_api.domain.enums.Gender;
import br.ufscar.pooa.cinema_api.domain.enums.MovieStatus;
import br.ufscar.pooa.cinema_api.domain.enums.Role;
import br.ufscar.pooa.cinema_api.domain.enums.RoomType;
import br.ufscar.pooa.cinema_api.domain.enums.SeatType;
import br.ufscar.pooa.cinema_api.domain.enums.Subtitle;
import br.ufscar.pooa.cinema_api.domain.repositories.client.IClientRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.genre.IGenreRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.manager.IManagerRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.movie.IMovieRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.room.IRoomRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.row.IRowRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.seat.ISeatRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.session.ISessionRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.theater.ITheaterRepository;

@Component
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    private final ITheaterRepository theaterRepository;
    private final IClientRepository clientRepository;
    private final IManagerRepository managerRepository;
    private final IGenreRepository genreRepository;
    private final IMovieRepository movieRepository;
    private final IRoomRepository roomRepository;
    private final IRowRepository rowRepository;
    private final ISeatRepository seatRepository;
    private final ISessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(
        ITheaterRepository theaterRepository,
        IClientRepository clientRepository,
        IManagerRepository managerRepository,
        IGenreRepository genreRepository,
        IMovieRepository movieRepository,
        IRoomRepository roomRepository,
        IRowRepository rowRepository,
        ISeatRepository seatRepository,
        ISessionRepository sessionRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.theaterRepository = theaterRepository;
        this.clientRepository = clientRepository;
        this.managerRepository = managerRepository;
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.rowRepository = rowRepository;
        this.seatRepository = seatRepository;
        this.sessionRepository = sessionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================================================================
    // Entry point
    // =========================================================================

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (theaterRepository.count() > 0) {
            System.out.println("Banco de dados já populado. Seeding não executado.");
            return;
        }

        System.out.println("Iniciando seeding...");

        Theater theater = createTheater();
        Client client = createClient();
        Manager manager = createManager();
        List<Room> rooms = createRooms(theater);
        Map<String, Genre> genres = createGenres();
        List<Session> sessions = createNowPlayingMoviesAndSessions(rooms, genres);
        createComingSoonMovies(genres);

        Seat firstSeat = seatRepository.findAll().get(0);
        printSeedInfo(client, manager, sessions.get(0), firstSeat);
    }

    // =========================================================================
    // Infrastructure
    // =========================================================================

    private Theater createTheater() {
        Address address = new Address()
            .setZipCode("13560-000")
            .setStreet("Rua do Teste")
            .setNumber("123")
            .setCity("São Carlos")
            .setNeighborhood("Centro")
            .setState("SP")
            .setCountry("Brasil")
            .setLatitude(-22.0176)
            .setLongitude(-47.8836);

        return theaterRepository.save(new Theater()
            .setName("CineMiranha")
            .setLogoUrl("https://placehold.co/200x200/292929/FFFFFF/png?text=CineMiranha")
            .setRooms(new ArrayList<>())
            .setAddress(address)
            .setManagers(new ArrayList<>()));
    }

    private Client createClient() {
        return clientRepository.save(new Client()
            .setEmail("cliente@teste.com")
            .setPassword(passwordEncoder.encode("123456"))
            .setCpf("123.456.789-00")
            .setName("Cliente de Teste")
            .setPhoneNumber("(11) 99999-9999")
            .setGender(Gender.MALE)
            .setBirthDate(LocalDate.now().minusYears(25))
            .setTickets(new ArrayList<>())
            .setRole(Role.CLIENT));
    }

    private Manager createManager() {
        Manager manager = new Manager();
        manager.setEmail("manager@teste.com");
        manager.setRole(Role.MANAGER);
        manager.setPassword(passwordEncoder.encode("123456"));
        manager.setCpf("987.654.321-00");
        manager.setBirthDate(LocalDate.now().minusYears(30));
        return managerRepository.save(manager);
    }

    // =========================================================================
    // Rooms & Seats
    // =========================================================================

    private List<Room> createRooms(Theater theater) {
        Room sala1 = saveRoom("Sala 1", RoomType.STANDARD, theater);
        Room sala2 = saveRoom("Sala 2", RoomType.SPECIAL, theater);
        Room sala3 = saveRoom("Sala 3", RoomType.STANDARD, theater);
        Room sala4 = saveRoom("Sala 4", RoomType.SPECIAL, theater);

        seedRoom(sala1, new char[]{'A', 'B', 'C'}, 8);
        seedRoom(sala2, new char[]{'A', 'B', 'C'}, 8);
        seedRoom(sala3, new char[]{'A', 'B', 'C'}, 8);
        seedRoom(sala4, new char[]{'A', 'B'}, 6);

        return List.of(sala1, sala2, sala3, sala4);
    }

    private Room saveRoom(String name, RoomType type, Theater theater) {
        return roomRepository.save(new Room()
            .setName(name)
            .setRoomType(type)
            .setTheater(theater)
            .setRows(new HashSet<>())
            .setSessions(new ArrayList<>()));
    }

    private void seedRoom(Room room, char[] rowLetters, int seatsPerRow) {
        for (char letter : rowLetters) {
            Row row = rowRepository.save(new Row()
                .setLetter(letter)
                .setRoom(room)
                .setSeats(new HashSet<>()));
            for (int i = 1; i <= seatsPerRow; i++) {
                seatRepository.save(new Seat()
                    .setNumber((char) ('0' + i))
                    .setRow(row)
                    .setTickets(new ArrayList<>())
                    .setSeatType(SeatType.STANDARD));
            }
        }
    }

    // =========================================================================
    // Genres
    // =========================================================================

    private Map<String, Genre> createGenres() {
        Genre acao = new Genre().setName("Ação");
        Genre comedia = new Genre().setName("Comédia");
        Genre drama = new Genre().setName("Drama");
        Genre ficcaoCientifica = new Genre().setName("Ficção Científica");
        Genre terror = new Genre().setName("Terror");
        Genre animacao = new Genre().setName("Animação");
        Genre familia = new Genre().setName("Família");
        Genre aventura = new Genre().setName("Aventura");
        Genre fantasia = new Genre().setName("Fantasia");
        Genre musical = new Genre().setName("Musical");
        Genre crime = new Genre().setName("Crime");

        genreRepository.saveAll(List.of(
            acao, comedia, drama, ficcaoCientifica, terror,
            animacao, familia, aventura, fantasia, musical, crime));

        Map<String, Genre> genres = new HashMap<>();
        genres.put("acao", acao);
        genres.put("comedia", comedia);
        genres.put("drama", drama);
        genres.put("ficcaoCientifica", ficcaoCientifica);
        genres.put("terror", terror);
        genres.put("animacao", animacao);
        genres.put("familia", familia);
        genres.put("aventura", aventura);
        genres.put("fantasia", fantasia);
        genres.put("musical", musical);
        genres.put("crime", crime);
        return genres;
    }

    // =========================================================================
    // Now Playing — Movies & Sessions
    // =========================================================================

    private List<Session> createNowPlayingMoviesAndSessions(List<Room> rooms, Map<String, Genre> g) {
        List<Session> sessions = new ArrayList<>();
        ZoneId tz = ZoneId.of("America/Sao_Paulo");

        // --- AINDA ESTOU AQUI ---
        Movie aindaEstouAqui = movieRepository.save(new Movie()
            .setTitle("AINDA ESTOU AQUI")
            .setDurationInSeconds(8100)
            .setCoverUrl("https://image.tmdb.org/t/p/w500/zNAw7jK8bwCK56rIW676pdgkwhd.jpg")
            .setTrailerUrl("https://www.youtube.com/watch?v=eruDAfbZvoY")
            .setSynopsis("Rio de Janeiro, início dos anos 1970. O país enfrenta o endurecimento da ditadura militar. Os Paiva — Rubens, Eunice e seus cinco filhos — vivem na frente da praia, numa casa de portas abertas para os amigos. Um dia, Rubens é levado por militares à paisana e desaparece. Eunice, cuja busca pela verdade sobre o destino de seu marido se estenderia por décadas, é obrigada a se reinventar e traçar um novo futuro para si e seus filhos. Baseado no livro biográfico de Marcelo Rubens Paiva.")
            .setAgeRating(AgeRating.FOURTEEN_YEARS)
            .setGenres(List.of(g.get("drama")))
            .setSessions(new ArrayList<>())
            .setStatus(MovieStatus.NOW_PLAYING));

        // 30/06
        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(3800)
            .setDate(LocalDateTime.of(2026, 6, 30, 14, 0))
            .setRoom(rooms.get(0)).setMovie(aindaEstouAqui).setTickets(new ArrayList<>())));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.SUBTITLED).setPriceInCents(3800)
            .setDate(LocalDateTime.of(2026, 6, 30, 19, 0))
            .setRoom(rooms.get(2)).setMovie(aindaEstouAqui).setTickets(new ArrayList<>())));

        // 01/07
        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(3800)
            .setDate(LocalDateTime.of(2026, 7, 1, 14, 0))
            .setRoom(rooms.get(0)).setMovie(aindaEstouAqui).setTickets(new ArrayList<>())));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.SUBTITLED).setPriceInCents(3800)
            .setDate(LocalDateTime.of(2026, 7, 1, 19, 0))
            .setRoom(rooms.get(3)).setMovie(aindaEstouAqui).setTickets(new ArrayList<>())));

        // 02/07
        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(3800)
            .setDate(LocalDateTime.of(2026, 7, 2, 16, 0))
            .setRoom(rooms.get(1)).setMovie(aindaEstouAqui).setTickets(new ArrayList<>())));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.SUBTITLED).setPriceInCents(3800)
            .setDate(LocalDateTime.of(2026, 7, 2, 20, 30))
            .setRoom(rooms.get(2)).setMovie(aindaEstouAqui).setTickets(new ArrayList<>())));

        // --- CHICO BENTO E A GOIABEIRA MARAVILHOSA ---
        Movie chicoBento = movieRepository.save(new Movie()
            .setTitle("CHICO BENTO E A GOIABEIRA MARAVILHOSA")
            .setDurationInSeconds(6000)
            .setCoverUrl("https://image.tmdb.org/t/p/w500/a5AuXy70HjNy7RJexXwNpoBGkg0.jpg")
            .setTrailerUrl("https://www.youtube.com/watch?v=jp54vQvfqEY")
            .setSynopsis("Chico Bento cresceu colhendo goiabas da goiabeira do Nhô Lau, mas quando o Dr. Agripino decide construir uma estrada que derrubará a árvore centenária, Chico e seus amigos farão de tudo para salvá-la.")
            .setAgeRating(AgeRating.GENERAL_AUDIENCE)
            .setGenres(List.of(g.get("animacao"), g.get("comedia"), g.get("familia")))
            .setSessions(new ArrayList<>())
            .setStatus(MovieStatus.NOW_PLAYING));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(2500)
            .setDate(LocalDateTime.now(tz).plusDays(2).plusHours(2))
            .setRoom(rooms.get(0)).setMovie(chicoBento).setTickets(new ArrayList<>())));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(2500)
            .setDate(LocalDateTime.now(tz).plusDays(2).plusHours(5))
            .setRoom(rooms.get(2)).setMovie(chicoBento).setTickets(new ArrayList<>())));

        // --- MOANA 2 ---
        Movie moana2 = movieRepository.save(new Movie()
            .setTitle("MOANA 2")
            .setDurationInSeconds(7200)
            .setCoverUrl("https://image.tmdb.org/t/p/w500/dnqgkKoIGf6hErzRm6VtaK1OJrD.jpg")
            .setTrailerUrl("https://www.youtube.com/watch?v=OLHrdmwW7KM")
            .setSynopsis("Moana embarca em uma nova e épica aventura pelos mares, seguindo uma mensagem ancestral que a leva além dos confins de Motunui.")
            .setAgeRating(AgeRating.GENERAL_AUDIENCE)
            .setGenres(List.of(g.get("animacao"), g.get("aventura"), g.get("familia")))
            .setSessions(new ArrayList<>())
            .setStatus(MovieStatus.NOW_PLAYING));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.THREE_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(5500)
            .setDate(LocalDateTime.now(tz).plusDays(3).plusHours(3))
            .setRoom(rooms.get(1)).setMovie(moana2).setTickets(new ArrayList<>())));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(4000)
            .setDate(LocalDateTime.now(tz).plusDays(3).plusHours(6))
            .setRoom(rooms.get(3)).setMovie(moana2).setTickets(new ArrayList<>())));

        // --- WICKED ---
        Movie wicked = movieRepository.save(new Movie()
            .setTitle("WICKED")
            .setDurationInSeconds(9600)
            .setCoverUrl("https://image.tmdb.org/t/p/w500/qcaKkLwIXCAxJtpetVYHniCvLZj.jpg")
            .setTrailerUrl("https://www.youtube.com/watch?v=FRJplq6nutA")
            .setSynopsis("A história de como Elphaba, a futura Bruxa Má do Oeste, e Glinda, a futura Bruxa Boa, tornaram-se amigas antes que as circunstâncias as separassem.")
            .setAgeRating(AgeRating.TWELVE_YEARS)
            .setGenres(List.of(g.get("fantasia"), g.get("musical")))
            .setSessions(new ArrayList<>())
            .setStatus(MovieStatus.NOW_PLAYING));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.SUBTITLED).setPriceInCents(4800)
            .setDate(LocalDateTime.now(tz).plusDays(4).plusHours(4))
            .setRoom(rooms.get(1)).setMovie(wicked).setTickets(new ArrayList<>())));

        sessions.add(sessionRepository.save(new Session()
            .setFormat(Format.TWO_D).setSubtitle(Subtitle.DUBBED).setPriceInCents(4800)
            .setDate(LocalDateTime.now(tz).plusDays(4).plusHours(7))
            .setRoom(rooms.get(3)).setMovie(wicked).setTickets(new ArrayList<>())));

        return sessions;
    }

    // =========================================================================
    // Coming Soon — Movies only
    // =========================================================================

    private void createComingSoonMovies(Map<String, Genre> g) {
        movieRepository.saveAll(List.of(
            new Movie()
                .setTitle("SHREK 5")
                .setDurationInSeconds(0)
                .setCoverUrl("https://image.tmdb.org/t/p/w500/8XwmMjwiQ91Rm0eU4AcxyFA3XUZ.jpg")
                .setTrailerUrl("https://www.youtube.com/watch?v=I9-wXs4KtrU")
                .setGenres(List.of(g.get("animacao"), g.get("comedia"), g.get("familia")))
                .setStatus(MovieStatus.COMING_SOON),
            new Movie()
                .setTitle("CAPITÃO AMÉRICA: ADMIRÁVEL MUNDO NOVO")
                .setDurationInSeconds(0)
                .setCoverUrl("https://image.tmdb.org/t/p/w500/5nbSgP8f5LMCI0PwVRRaHJaUmR3.jpg")
                .setTrailerUrl("https://www.youtube.com/watch?v=FEa9pPqGhPY")
                .setGenres(List.of(g.get("acao"), g.get("ficcaoCientifica")))
                .setStatus(MovieStatus.COMING_SOON),
            new Movie()
                .setTitle("CORINGA 2")
                .setDurationInSeconds(0)
                .setCoverUrl("https://image.tmdb.org/t/p/w500/9RmVr8dPWicFyPZ5JCQK3NcBNB5.jpg")
                .setTrailerUrl("https://www.youtube.com/watch?v=_OKAwz2MsJs")
                .setGenres(List.of(g.get("crime"), g.get("drama"), g.get("musical")))
                .setStatus(MovieStatus.COMING_SOON)
        ));
    }

    // =========================================================================
    // Utils
    // =========================================================================

    private void printSeedInfo(Client client, Manager manager, Session firstSession, Seat firstSeat) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("Seeding Finalizado!");
        System.out.println("Use os seguintes IDs para testar tickets no Insomnia:");
        System.out.println(">>> clientId:  " + client.getId());
        System.out.println(">>> managerId: " + manager.getId());
        System.out.println(">>> sessionId: " + firstSession.getId());
        System.out.println(">>> seatId:    " + firstSeat.getId());
        System.out.println("------------------------------------------------------------\n");
    }
}
