/* Initial beliefs and rules */

last_dir(null).
onfire.

/* Initial goals */
!checkExcavator1.

/* Plans */
+!checkExcavator1:  onfire  <-
    ?excavator1(_,X,Y);
    !pos(X,Y);
    !checkThis(X,Y);
    !!checkExcavator2.


+!checkExcavator2:  onfire  <-
    ?excavator2(_,X,Y);
    !pos(X,Y);
    !checkThis(X,Y);
    !!checkExcavator3.

+!checkExcavator3:  onfire  <-
    ?excavator3(_,X,Y);
    !pos(X,Y);
    !checkThis(X,Y);
    !!checkExcavator4.

+!checkExcavator4:  onfire  <-
    ?excavator4(_,X,Y);
    !pos(X,Y);
    !checkThis(X,Y);
    !!checkExcavator1.

+!checkExcavator1:  true <- .print("waiting1").
+!checkExcavator2:  true <- .print("waiting2").
+!checkExcavator3:  true <- .print("waiting3").
+!checkExcavator4:  true <- .print("waiting4").

+!checkThis(X,Y) : jia.check(X,Y) <- true.

+!checkThis(X,Y) : true <-
    .print("ÉgS" , Y);
    !pos(X,Y);
    .broadcast(tell, repair(X,Y));
    -onfire.

+mehetTovabb[source(mechanic2)] : true <-
    .print("karbantarto sent done");
    +nemkelljavitas;
    -mehetTovabb[source(mechanic2)];
    !checkSensor1.

+!pos(X,Y) : pos(X,Y) <- true.

+!pos(X,Y) : pos(X,Y) <- .print("I've reached [",X,",",Y,"].").

+!pos(X,Y) : not pos(X,Y) & onfire <-
    !next_step(X,Y);
    !pos(X,Y).

+!next_step(X,Y) : pos(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not pos(_,_) <- !next_step(X,Y).

// failure handling -> start again!
-!next_step(X,Y) : true <-
    .print("Failed next_step to ", X, "x", Y, " fixing and trying again!");
    -+last_dir(null);
    !next_step(X,Y).
