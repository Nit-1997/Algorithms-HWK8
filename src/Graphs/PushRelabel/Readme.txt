This code is a Java implementation of the Push-Relabel algorithm for maximum flow in a flow network.
The GraphNodeImpl class represents a node in the flow network and contains fields for the source and sink nodes,
the number of vertices in the network, the capacities and flows of the edges,
and the excess flow and height of each node.
It also includes a method for building a neighbor list from edge capacities.

The PushRelabel class contains methods for pushing flow from one node to another (push),
relabeling nodes to increase their height (relabel), discharging excess flow from a node (discharge),
initializing the algorithm (init), moving an index to the front of an array (moveIndexToFrontOfAry),
and getting the maximum flow of the network (getMaxFlow).

The push method calculates the amount of flow that can be pushed from one node to another based on
the excess flow at the first node and the residual capacity of the edge between them.
It then updates the excess flow at both nodes and the flows along the edge accordingly.

The relabel method increases the height of a node if it has excess flow but cannot push it to any of
its neighbors due to their heights being equal or greater. It does this by finding the minimum height
among its neighbors with positive residual capacity and setting its own height to one more than that.

The discharge method repeatedly pushes flow from a node with excess flow to its neighbors until it
has no more excess flow or all its neighbors have been tried. If it still has excess flow after
trying all its neighbors, it relabels itself to increase its height.

The init method sets up the algorithm by setting the height of the source node to the number of
vertices in the network and temporarily allowing it to have infinite excess flow. It then pushes as
much flow as possible from the source to its neighbors and sets its excess flow to negative
the sum of outflows.

The moveIndexToFrontOfAry method moves an index in an array to the front by shifting all elements
between it and the front one position to the right.

Finally, the getMaxFlow method runs the algorithm by repeatedly discharging nodes with excess flow
until no more nodes have excess flow. The maximum flow is then equal to the excess flow at the
sink node.

