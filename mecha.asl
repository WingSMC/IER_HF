/* Initial beliefs and rules */

last_dir(null). // the last movement I did
~hazamegy.

/* Initial goals */
!wait.

/* Plans */
+!wait : true <- !!wait.

/* karbantartásra van szükség */
+repair(X,Y)[source(drone1)]: ~hazamegy <-
    !poz(X,Y);
    .print("repair");
    jia.repair(X,Y);
    -repair(X,Y)[source(drone1)];
    +hazamegy;
    !poz(2,5);
    -hazamegy;
    .print("sending done");
    .broadcast(tell,mehetTovabb).

+repair(X,Y)[source(drone2)]: ~hazamegy <- 
    !poz(X,Y);
    .print("repair");
    jia.repair(X,Y);
    -repair(X,Y)[source(drone2)];
    +hazamegy;
    !poz(2,5);
    -hazamegy;
    .print("sending done");
    .broadcast(tell,mehetTovabb).


/* Moving */
+!poz(X,Y) : poz(X,Y) <- .print("I've reached ",X,"x",Y).

+!poz(X,Y) : not poz(X,Y) <-
    !next_step(X,Y);
    !poz(X,Y).

+!next_step(X,Y): poz(AgX,AgY) <-
    jia.get_direction(AgX, AgY, X, Y, D);
    -+last_dir(D);
    do(D).

// I still do not know my position
+!next_step(X,Y) : not poz(_,_) <-
    !next_step(X,Y);
    .print("nextStepnotpos").

// failure handling -> start again!
-!next_step(X,Y) : true <-
    .print("Failed next_step to ", X,"x",Y," fixing and trying again!");
    -+last_dir(null);
    !next_step(X,Y).
