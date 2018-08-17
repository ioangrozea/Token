package rest.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static UserRepository ourInstance = new UserRepository();
    private List<User> localUsers;
    private Long idCount=0L;

    public static UserRepository getInstance() {
        return ourInstance;
    }


    private UserRepository() {
        localUsers = new ArrayList<>();
        add(new User("Alex1", "Luca1", "alexluca1", "parola1"));
        add(new User("Alex2", "Luca2", "alexluca2", "parola2"));
        add(new User("Alex3", "Luca3", "alexluca3", "parola3"));
        idCount = 0L;
    }

    public User add(User user){
        user.setId(idCount++);
        localUsers.add(user);
        return user;
    }

    public List<User> findAll(){
        return localUsers;
    }

    public Optional<User> getUserById(Long id){
        return localUsers.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

    public Optional<User> update(User user){
        Optional<User> optionalUser = getUserById(user.getId());
        optionalUser.map(foundUser -> {foundUser.setFirstName(user.getFirstName());
                                        foundUser.setLastName(user.getLastName());
                                        foundUser.setUsername(user.getUsername());
                                        foundUser.setPassword(user.getPassword());
                                        return foundUser;});
        return optionalUser;
    }


    public void delete(Long id){
        getUserById(id).ifPresent(x -> localUsers.remove(x));
    }

    public static void main(String[] args) {
        List<User> users = UserRepository.getInstance().findAll();

        for(User u : users){
            System.out.println(u);
        }

        UserRepository.getInstance().add(new User("John","White","hat","hat"));
        UserRepository.getInstance().findAll().forEach(System.out::println);
    }
}
