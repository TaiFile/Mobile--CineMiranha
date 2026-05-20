package br.ufscar.pooa.cinema_api.infrastructure.database;

import br.ufscar.pooa.cinema_api.domain.repositories.client.IClientRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.genre.IGenreRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.manager.IManagerRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.movie.IMovieRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.room.IRoomRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.row.IRowRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.seat.ISeatRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.session.ISessionRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.theater.ITheaterRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    public DatabaseSeeder(ITheaterRepository theaterRepository, IClientRepository clientRepository,
        IManagerRepository managerRepository,
        IGenreRepository genreRepository, IMovieRepository movieRepository,
        IRoomRepository roomRepository, IRowRepository rowRepository,
        ISeatRepository seatRepository, ISessionRepository sessionRepository,
        PasswordEncoder passwordEncoder) {
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

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (theaterRepository.count() > 0) {
            System.out.println("Banco de dados já populado. Seeding não executado.");
            return;
        }

        System.out.println("Iniciando seeding MÍNIMO para criação de Ticket...");

        // 1. Theater
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
        Theater theater = new Theater()
            .setName("Cine Teste")
            .setLogoUrl("http://logo.url/logo.png")
            .setRooms(new ArrayList<>())
            .setAddress(address)
            .setManagers(new ArrayList<>());
        Theater savedTheater = theaterRepository.save(theater);

        // 2. Client
        Client client = new Client()
            .setEmail("cliente@teste.com")
            .setPassword(passwordEncoder.encode("123456"))
            .setCpf("123.456.789-00")
            .setName("Cliente de Teste")
            .setPhoneNumber("(11) 99999-9999")
            .setGender(Gender.MALE)
            .setBirthDate(LocalDate.now().minusYears(25))
            .setTickets(new ArrayList<>())
            .setRole(Role.CLIENT);
        Client savedClient = clientRepository.save(client);

        Manager manager = new Manager();
        manager.setEmail("manager@teste.com");
        manager.setRole(Role.MANAGER);
        manager.setPassword(passwordEncoder.encode("123456"));
        manager.setCpf("123.456.789-00");
        manager.setBirthDate(LocalDate.now().minusYears(25));
        Manager savedManager = managerRepository.save(manager);

        // 3. Room
        Room room = new Room()
            .setName("Sala Teste 1")
            .setRoomType(RoomType.STANDARD)
            .setTheater(savedTheater)
            .setRows(new HashSet<>())
            .setSessions(new ArrayList<>());
        Room savedRoom = roomRepository.save(room);

        // 4. Row
        Row row = new Row()
            .setLetter('A')
            .setRoom(savedRoom)
            .setSeats(new HashSet<>());
        Row savedRow = rowRepository.save(row);

        // 5. Seat
        Seat seat = new Seat()
            .setNumber('1')
            .setRow(savedRow)
            .setTickets(new ArrayList<>())
            .setSeatType(SeatType.STANDARD);
        Seat savedSeat = seatRepository.save(seat);

        // 6. Genre
        Genre genre = new Genre()
            .setName("Ação")
            .setMovies(new ArrayList<>());
        Genre savedGenre = genreRepository.save(genre);

        // 7. Movie
        List<Genre> genres = new ArrayList<>();
        genres.add(savedGenre);
        Movie movie = new Movie()
            .setAgeRating(AgeRating.FOURTEEN_YEARS)
            .setGenres(genres)
            .setSessions(new ArrayList<>())
            .setDurationInSeconds(7500)
            .setTrailerUrl("http://trailer.url/trailer.mp4")
            .setCoverUrl("http://cover.url/cover.jpg")
            .setSynopsis("Um filme de teste para uma API incrível.")
            .setTitle("Filme de Teste")
            .setStatus(MovieStatus.NOW_PLAYING);
        Movie savedMovie = movieRepository.save(movie);

        // 8. Session
        Session session = new Session()
            .setFormat(Format.TWO_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(1).plusMinutes(1))
            .setSubtitle(Subtitle.DUBBED)
            .setPriceInCents(3500)
            .setRoom(savedRoom)
            .setMovie(savedMovie)
            .setTickets(new ArrayList<>());
        Session savedSession = sessionRepository.save(session);

        // Adding more rooms, genres, movies and sessions

        Room room2 = new Room()
            .setName("Sala Teste 2")
            .setRoomType(RoomType.SPECIAL)
            .setTheater(savedTheater)
            .setRows(new HashSet<>())
            .setSessions(new ArrayList<>());
        Room savedRoom2 = roomRepository.save(room2);

        Row rowB = new Row().setLetter('B').setRoom(savedRoom2).setSeats(new HashSet<>());
        rowRepository.save(rowB);
        Seat seat2 = new Seat().setNumber('2').setRow(rowB).setSeatType(SeatType.PLUS_SIZE);
        seatRepository.save(seat2);

        Genre comedyGenre = new Genre().setName("Comédia");
        Genre dramaGenre = new Genre().setName("Drama");
        Genre scifiGenre = new Genre().setName("Ficção Científica");
        Genre horrorGenre = new Genre().setName("Terror");

        genreRepository.saveAll(List.of(comedyGenre, dramaGenre, scifiGenre, horrorGenre));

        Movie movie2 = new Movie()
            .setTitle("The Godcomputer")
            .setSynopsis(
                "In a future where an AI governs the world, a small group of rebels attempts to overthrow it.")
            .setDurationInSeconds(8100)
            .setAgeRating(AgeRating.SIXTEEN_YEARS)
            .setGenres(List.of(scifiGenre, dramaGenre))
            .setCoverUrl("https://picsum.photos/200/300?random=1")
            .setTrailerUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            .setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movie2);

        Session session2 = new Session()
            .setFormat(Format.TWO_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(2))
            .setSubtitle(Subtitle.SUBTITLED)
            .setPriceInCents(4000)
            .setRoom(savedRoom)
            .setMovie(movie2)
            .setTickets(new ArrayList<>());
        sessionRepository.save(session2);

        Session session3 = new Session()
            .setFormat(Format.THREE_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(5))
            .setSubtitle(Subtitle.DUBBED)
            .setPriceInCents(5000)
            .setRoom(savedRoom2)
            .setMovie(movie2)
            .setTickets(new ArrayList<>());
        sessionRepository.save(session3);

        Movie movie3 = new Movie()
            .setTitle("Weekend at Bernie's 3")
            .setSynopsis(
                "Two insurance salesmen try to pretend their murdered boss is still alive, leading to a series of comical situations.")
            .setDurationInSeconds(5820)
            .setAgeRating(AgeRating.TWELVE_YEARS)
            .setGenres(List.of(comedyGenre))
            .setCoverUrl("https://picsum.photos/200/300?random=2")
            .setTrailerUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            .setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movie3);

        Session session4 = new Session()
            .setFormat(Format.TWO_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(1).plusHours(3))
            .setSubtitle(Subtitle.DUBBED)
            .setPriceInCents(3000)
            .setRoom(savedRoom)
            .setMovie(movie3)
            .setTickets(new ArrayList<>());
        sessionRepository.save(session4);

        Movie movie4 = new Movie()
            .setTitle("The Haunting of Hill House 2")
            .setSynopsis(
                "A family is confronted with haunting memories of their old home and the terrifying events that drove them from it.")
            .setDurationInSeconds(7800)
            .setAgeRating(AgeRating.EIGHTEEN_YEARS)
            .setGenres(List.of(horrorGenre, dramaGenre))
            .setCoverUrl("https://picsum.photos/200/300?random=3")
            .setTrailerUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            .setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movie4);

        Session session5 = new Session()
            .setFormat(Format.TWO_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(2).plusHours(4))
            .setSubtitle(Subtitle.ORIGINAL)
            .setPriceInCents(4500)
            .setRoom(savedRoom2)
            .setMovie(movie4)
            .setTickets(new ArrayList<>());
        sessionRepository.save(session5);

        // Adding movies from user request

        Genre animationGenre = new Genre().setName("Animação");
        Genre familyGenre = new Genre().setName("Família");
        Genre adventureGenre = new Genre().setName("Aventura");
        Genre fantasyGenre = new Genre().setName("Fantasia");
        Genre musicalGenre = new Genre().setName("Musical");
        Genre crimeGenre = new Genre().setName("Crime");
        genreRepository.saveAll(
            List.of(animationGenre, familyGenre, adventureGenre, fantasyGenre, musicalGenre,
                crimeGenre));

        // emCartaz
        Movie movieAindaEstouAqui = new Movie()
            .setTitle("AINDA ESTOU AQUI")
            .setDurationInSeconds(8100)
            .setCoverUrl("/Images/ainda-estou-aqui.jpg")
            .setTrailerUrl("https://www.youtube.com/embed/_NzqP0jmk3o")
            .setSynopsis(
                "Rio de Janeiro, início dos anos 1970. O país enfrenta o endurecimento da ditadura militar. Os Paiva — Rubens, Eunice e seus cinco filhos — vivem na frente da praia, numa casa de portas abertas para os amigos. Um dia, Rubens é levado por militares à paisana e desaparece. Eunice, cuja busca pela verdade sobre o destino de seu marido se estenderia por décadas, é obrigada a se reinventar e traçar um novo futuro para si e seus filhos. Baseado no livro biográfico de Marcelo Rubens Paiva.")
            .setAgeRating(AgeRating.FOURTEEN_YEARS)
            .setGenres(List.of(dramaGenre))
            .setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movieAindaEstouAqui);

        Session sessionAindaEstouAqui = new Session()
            .setFormat(Format.TWO_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(3).plusHours(1))
            .setSubtitle(Subtitle.ORIGINAL)
            .setPriceInCents(3800)
            .setRoom(savedRoom)
            .setMovie(movieAindaEstouAqui);
        sessionRepository.save(sessionAindaEstouAqui);

        Movie movieChicoBento = new Movie()
            .setTitle("CHICO BENTO E A GOIABEIRA MARAVILHOSA")
            .setDurationInSeconds(6000)
            .setCoverUrl("/Images/chico-bento.jpg")
            .setAgeRating(AgeRating.GENERAL_AUDIENCE)
            .setGenres(List.of(animationGenre, comedyGenre, familyGenre))
            .setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movieChicoBento);

        Session sessionChicoBento = new Session()
            .setFormat(Format.TWO_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(4).plusHours(2))
            .setSubtitle(Subtitle.DUBBED)
            .setPriceInCents(2500)
            .setRoom(savedRoom2)
            .setMovie(movieChicoBento);
        sessionRepository.save(sessionChicoBento);

        Movie movieMoana2 = new Movie()
            .setTitle("MOANA 2")
            .setDurationInSeconds(7200)
            .setCoverUrl("/Images/moana-2.jpg")
            .setAgeRating(AgeRating.GENERAL_AUDIENCE)
            .setGenres(List.of(animationGenre, adventureGenre, familyGenre))
            .setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movieMoana2);

        Session sessionMoana2 = new Session()
            .setFormat(Format.THREE_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(5).plusHours(3))
            .setSubtitle(Subtitle.DUBBED)
            .setPriceInCents(5500)
            .setRoom(savedRoom2)
            .setMovie(movieMoana2);
        sessionRepository.save(sessionMoana2);

        Movie movieWicked = new Movie()
            .setTitle("WICKED")
            .setDurationInSeconds(9600)
            .setCoverUrl("/Images/wicked.jpg")
            .setAgeRating(AgeRating.TWELVE_YEARS)
            .setGenres(List.of(fantasyGenre, musicalGenre))
            .setStatus(MovieStatus.NOW_PLAYING);
        movieRepository.save(movieWicked);

        Session sessionWicked = new Session()
            .setFormat(Format.TWO_D)
            .setDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).plusDays(6).plusHours(4))
            .setSubtitle(Subtitle.SUBTITLED)
            .setPriceInCents(4800)
            .setRoom(savedRoom)
            .setMovie(movieWicked);
        sessionRepository.save(sessionWicked);

        // emBreve
        Movie movieShrek5 = new Movie()
            .setTitle("SHREK 5")
            .setDurationInSeconds(0)
            .setCoverUrl("/Images/shrek-5.jpg")
            .setGenres(List.of(animationGenre, comedyGenre, familyGenre))
            .setStatus(MovieStatus.COMING_SOON);
        movieRepository.save(movieShrek5);

        Movie movieCapitaoAmerica = new Movie()
            .setTitle("CAPITÃO AMÉRICA: ADMIRÁVEL MUNDO NOVO")
            .setDurationInSeconds(0)
            .setCoverUrl("/Images/capitao-america.jpg")
            .setGenres(List.of(savedGenre, scifiGenre)) // Ação e Ficção Científica
            .setStatus(MovieStatus.COMING_SOON);
        movieRepository.save(movieCapitaoAmerica);

        Movie movieCoringa2 = new Movie()
            .setTitle("CORINGA 2")
            .setDurationInSeconds(0)
            .setCoverUrl("/Images/coringa-2.jpg")
            .setGenres(List.of(crimeGenre, dramaGenre, musicalGenre))
            .setStatus(MovieStatus.COMING_SOON);
        movieRepository.save(movieCoringa2);

        System.out.println("\n------------------------------------------------------------");
        System.out.println("Seeding Mínimo Finalizado!");
        System.out.println("Use os seguintes IDs para criar um Ticket no Insomnia:");
        System.out.println(">>> clientId: " + savedClient.getId());
        System.out.println(">>> managerId: " + savedManager.getId());
        System.out.println(">>> sessionId: " + savedSession.getId());
        System.out.println(">>> seatId: " + savedSeat.getId());
        System.out.println("------------------------------------------------------------\n");
    }
}
