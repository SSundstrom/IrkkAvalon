package AvalonGame.roles;

public class Percival extends AbstractRole {
    public Percival() {
        super("Percival",   //Name
                false,       //see Evil
                true,      //see Merlin
                false,       //see Mordred
                false,       //seen as Evil
                false,      //seen as Merlin
                false,       //get to vote fail
                true);      //Unique
    }
}
