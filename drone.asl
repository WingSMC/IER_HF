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

+!checkExcavator1:  true <- .print("waiting").
+!checkExcavator2:  true <- .print("waiting").
+!checkExcavator3:  true <- .print("waiting").
+!checkExcavator4:  true <- .print("waiting").

+!checkThis(X,Y) : jia.check(X,Y) <- true.

+!checkThis(X,Y) : true <- .print("Ég");
    .print(X,Y);
    .broadcast(tell, repair(X,Y));
    !pos(X,Y);
    .print("Request sent to mechanic");
    -onfire.

+mehetTovabb[source(mecha2)] : true <-
    .print("karbantarto sent done");
    +nemkelljavitas;
    -mehetTovabb[source(mecha2)];
    !checkSensor1.

+!pos(X,Y) : pos(X,Y) <- true.

+!pos(X,Y) : pos(X,Y) <- .print("I've reached [",X,",",Y,"].").

+!pos(X,Y) : not pos(X,Y) & onfire <-
    !next_step(X,Y);
    !pos(X,Y).

+!next_step(X,Y):pos(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    .print("drone ",AgX," ", AgY," ",X," ",Y);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not pos(_,_) <- !next_step(X,Y).

// failure handling -> start again!
-!next_step(X,Y) : true <-
    .print("Failed next_step to ", X, "x", Y, " fixing and trying again!");
    -+last_dir(null);
    !next_step(X,Y).

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
