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
        System.out.println("coords: " + x + y);
        if(exL != null)
            System.out.print(exL.getIsFaulty());
        else
            System.out.print("null");
        if(exR != null)
            System.out.print(exR.getIsFaulty());
        else
            System.out.print("null");
        if(exT != null)
            System.out.print(exT.getIsFaulty());
        else
            System.out.print("null");
        if(exB != null)
            System.out.println(exB.getIsFaulty());
        else
            System.out.println("null");
        var ex = Stream.of(exL, exR, exT, exB).filter(e -> e != null && e.getIsFaulty()).findFirst().orElse(null);
        System.out.println(ex);
        if (ex == null)
            return false;
        ex.isFaulty(false);
        return true;
    }
}
