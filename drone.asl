/* Initial beliefs and rules */

last_dir(null).
nemkelljavitas.

/* Initial goals */
!checkExcavator1.

/* Plans */
+!checkExcavator1:  nemkelljavitas  <- ?excavator1(_,X,Y);
 !pos(X,Y);
 !!checkExcavator2.


+!checkExcavator2:  nemkelljavitas  <- ?excavator2(_,X,Y);
 !pos(X,Y);
 !!checkExcavator3.
 
 
+!checkExcavator3:  nemkelljavitas  <- ?excavator3(_,X,Y);
 !pos(X,Y);
 !!checkExcavator4.
 
 
 +!checkExcavator4:  nemkelljavitas  <- ?excavator4(_,X,Y);
  !pos(X,Y);
  !!checkExcavator1.
 

 +!checkExcavator1:  true <- .print("waiting1").
 +!checkExcavator2:  true <- .print("waiting2").
 +!checkExcavator3:  true <- .print("waiting3").
 +!checkExcavator4:  true <- .print("waiting4").
 -nemkelljavitas.



+!pos(X,Y) : pos(X,Y) <- true.

+!pos(X,Y) : pos(X,Y) <- .print("I've reached ",X,"x",Y).

+!pos(X,Y) : not pos(X,Y) & nemkelljavitas <- !next_step(X,Y);
!pos(X,Y).
+!next_step(X,Y)
:  pos(AgX,AgY)
<- jia.get_direction(AgX, AgY, X, Y, D);
-+last_dir(D);
do(D).
  
+!next_step(X,Y) : not pos(_,_) // I still do not know my position
<- !next_step(X,Y).
   
-!next_step(X,Y) : true  // failure handling -> start again!
<- .print("Failed next_step to ", X, "x", Y, " fixing and trying again!");
-+last_dir(null);
!next_step(X,Y).

