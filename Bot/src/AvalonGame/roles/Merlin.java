package AvalonGame.roles;

public class Merlin extends AbstractRole {
        public Merlin() {
        super("Merlin",     //Name
                true,       //see Evil
                false,      //see Merlin
                false,       //see Mordred
                false,       //seen as Evil
                true,      //seen as Merlin
                false,       //get to vote fail
                true);      //Unique
        }
}
