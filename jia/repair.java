package jia;



import java.util.stream.Stream;

import drones.CaveModel;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Term;
import jason.asSyntax.NumberTerm;

public class repair extends DefaultInternalAction {
    CaveModel model = CaveModel.get();

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] terms) throws Exception {
        int x = (int) ((NumberTerm) terms[0]).solve();
        int y = (int) ((NumberTerm) terms[1]).solve();
        var exL = model.getExcavator(x - 1, y);
        var exR = model.getExcavator(x + 1, y);
        var exT = model.getExcavator(x, y - 1);
        var exB = model.getExcavator(x, y + 1);
        var ex = Stream.of(exL, exR, exT, exB).filter(e -> e != null && e.isFaulty()).findFirst().orElse(null);
        if (ex == null)
            return false;
        ex.isFaulty(false);
        return true;
    }
}
