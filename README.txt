				Cache Library

How to compile: The jar file is project file from Netbeans. After importing it to the IDE,
LoginServiceImplementation class contains the main method with some test cases.

from command prompt:
compile 1) LoginService.Java
		2) CacheLibrary.Java
		3) FakeDBAccess.Java
		4) LoginServiceImplementation.Java

				
How to run : main() method is in LoginServiceImplementation. We can run the code from there.
It has few test cases. In this class there is a variable testing change it true to run the
last test case. Last test case required some different configuration than other test cases.

Q- Decisions:
A-
1) Why not Queue and Map in Cache:
Using a ConcurrentLinkedQueue in cache could have reduced the O(n) 
cost of adding to cache to O(1). Initially I was using queue to keep the
userId which was natural ordered with time in which they logged in.

With Queue there were some limitation viz. when someone updates the 
login time of user which is before the current time then in queue I will
have to rearrange everything since queue is in sequence of userId.
(aligned by time)
I might not be clear in what I was trying to say so here is an example:

users logged in the system
UID    Time
1 -    6:00 AM 
2 -    7:00 AM
3 -    8:00 AM

So the queue for the above 3 entry will be 
1-2-3 (which is in natural ordering with respect to time)

so this queue will work fine until I use the setLastLoginForUser from FakeDB
because it can set any date with any userId and the time can be less than current
time which will break the natural ordering in queue. Resulting in rearranging
of Queue every time. I hope I was clear in explaining why I didn't use queue
with map.

2) Database updates and cache sync :
I struggled alot on how to keep the cache updated. Because if the cache is
not updated with every update to DB its of no use using cache.
Finally I ended up with a solution to update the cache as soon as any query
hit the DB and made changes to setLastLoginForUser() method of FakeDB class.

3) Static methods in Cache : 
Major concern I had since beginning was keeping the cache updated with 
any update in database. So the solution which I could think of was make an
update to cache whenever we are updating the DB. That was the reason I choose
keeping methods of cache static so that it can be accessible from FakeDB class.



Q- Reasons for implementing cache :
A-
1) speed and performance : By implementing cache we gain more speed in executing
query thereby gaining performance in the application.
2) better user experience : Better performance means faster and better user
experience.
3) With cache we will be able to handle more traffic.



Q- How does this cache improve performance:
A- Cache helps to avoid hitting DB for every query. Its like a small copy of 
DB in ur program which will help in answering most of the frequent queries.



Q- Various usage patterns that make the cache more or less effective in terms of performance? 
A-  Less Effective : When there are more insert queries than retrieve query. Because main 
motive of cache is to reduce the access time from DB. Suppose calling UseJustLoggedIn 1000 times 
and hasUserLoggedInWithin24 just once.

More Effective: It will be very effective if the insert query are less and retrieve queries 
are more. This will serve the purpose of the cache. Example calling hasUserLoggedInWithin24 1000
times and calling UseJustLoggedIn 0 times.



Q- Why the cache will be more effective under some scenarios and less effective under others.
A- Two scenarios explained above tells us in some condition cache can be very effective and 
in some least effective. Cache reasons its very effective in second scenario is because it will
reduce the number of time we get the results from DB. Getting the results from DB is an expensive
task. Caching helps in reducing that cost.
If cache is not used properly then it leads to inefficiency because then ur waiting space 
and computation cost.