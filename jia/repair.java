package jia;



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
        int i = (int) ((NumberTerm) terms[0]).solve();
        var ex = model.getExcavator(i);
        if (ex == null)
            return false;
        ex.isFaulty(false);
        return true;
    }
}
