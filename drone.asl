+step(_) : alive_neighbors(0) <-die.
+step(_) : alive_neighbors(3) <-live.
+step(_) <- skip.

// Bid for next move @ empty & reachable cell

// If moved broadcast findings
