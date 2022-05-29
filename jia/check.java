package jia;

import java.util.concurrent.ThreadLocalRandom;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;
import jason.environment.grid.Location;

public class check extends DefaultInternalAction {

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
        if(randomNum > 75)
            return false;
        return true;
    }
}
