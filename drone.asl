last_dir(null).
issafe.

!checkExcavator1.

+!checkExcavator1:  issafe  <-
    ?excavator1(_,X,Y);
    !pos(X,Y);
    !checkThis1(X,Y);
    !!checkExcavator2.

+!checkExcavator2:  issafe  <-
    ?excavator2(_,X,Y);
    !pos(X,Y);
    !checkThis2(X,Y);
    !!checkExcavator3.

+!checkExcavator3:  issafe  <-
    ?excavator3(_,X,Y);
    !pos(X,Y);
    !checkThis3(X,Y);
    !!checkExcavator4.

+!checkExcavator4:  issafe  <-
    ?excavator4(_,X,Y);
    !pos(X,Y);
    !checkThis4(X,Y);
    !!checkExcavator1.

+!checkExcavator1:  true <- .print("Waiting for mechanic at Excavator1.").
+!checkExcavator2:  true <- .print("Waiting for mechanic at Excavator2.").
+!checkExcavator3:  true <- .print("Waiting for mechanic at Excavator3.").
+!checkExcavator4:  true <- .print("Waiting for mechanic at Excavator4.").

+!checkThis1(X,Y) : jia.check(X,Y) <- true.
+!checkThis1(X,Y) : true <- .print("Excavator1 is burning.");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Help request sent to mechanic from Excavator1.");
    -issafe.

+!checkThis2(X,Y) : jia.check(X,Y) <- true.
+!checkThis2(X,Y) : true <- .print("Excavator2 is burning.");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Help request sent to mechanic from Excavator2.");
    -issafe.

+!checkThis3(X,Y) : jia.check(X,Y) <- true.
+!checkThis3(X,Y) : true <- .print("Excavator2 is burning.");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Help request sent to mechanic from Excavator3.");
    -issafe.

+!checkThis4(X,Y) : jia.check(X,Y) <- true.
+!checkThis4(X,Y) : true <- .print("Excavator2 is burning.");
    .broadcast(tell, repair(X + 1,Y));
    !pos(X,Y);
    .print("Help request sent to mechanic from Excavator4.");
    -issafe.

+keepmoving[source(mecha2)] : true <-
    .print("Mechanic recieved the request");
    +issafe;
    -keepmoving[source(mecha2)];
    !checkExcavator1.

+!pos(X,Y) : pos(X,Y) <- true.

+!pos(X,Y) : pos(X,Y) <- .print("Drone has reached position ",X,", ",Y,".").

+!pos(X,Y) : not pos(X,Y) & issafe <-
    !next_step(X,Y);
    !pos(X,Y).

+!next_step(X,Y):pos(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

+!next_step(X,Y) : not pos(_,_) <- !next_step(X,Y).

-!next_step(X,Y) : true <-
    .print("Drone failed moving to ", X, ", ", Y, ", trying again!");
    -+last_dir(null);
    !next_step(X,Y).

+!next_step(X,Y) : pos(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

+!next_step(X,Y) : not pos(_,_) <- !next_step(X,Y).
