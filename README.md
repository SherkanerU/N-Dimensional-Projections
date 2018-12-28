# N-Dimensional-Projections
Visualizes projections from n dimensional space to 2 dimensional space.  In the form n -> n-1 -> ... -> 2 

This isn't a formal mathematical project.  It is really just a curiosity of mine to visualize higher dimensional objects.  I worked a lot on it in highschool.  I am just reworking to code and making it look nicer, as well as making the mathematics more coherent.  I did not know formal linear algebra at the time so some of the naming is a bit strange but I try to keep it as rigoruous as possible.

I also implement a basic matrix math library in Matrix.java.  I am aware that this exists but this whole project more or less exists as an interesting application of elementary linear algebra that I thought of before formal math training.  So I thought I would just make it for fun.  I'm currently working on it, I'm using a matrix of cofactors with determinat method to calculate inverses.  Kind of slow but its really cool to me!  This shouldn't really be a problem for smaller matrices!  I might also write a guassian elimation method later and compare speeds.

There is also a VectorSpace class which is pretty useful for other reasons.  It can test for linear independance and perform the gram-schmidt process on sets of vectors!
