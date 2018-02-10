# 2018-Competition-Bot

This is the repository for the 2018 competition bot code.

There is NO code in the main branch.
Instead, please switch to the `competition-production` branch to get the final competition code. This code is guaranteed to work, so you will not be pushing code to the `competition-production` branch at any time.

If you will be pushing new code, switch to the `competition-testing` branch. Submit a pull request to have your code moved into the production branch. If you intend to work on an involved autonomous code, you should create your own branch from `competition-testing` to keep your code. When your auto is done, it can be pull requested into the testing branch, and then into the production branch.

At first, we will only be committing to the `competition-test` branch. Once the robot is bagged and we can no longer test with the real robot, we will switch to commiting to the `practice-test`branch. Pull requests into `practice-production` work the same as into `competition-production`, except in this case the only critical need for `practice-production` is code that works for driver practice using the practice robot. 

As we write code for the practice robot, we will periodically make pull requests into the `competition-test` branch when we reach a point where something is finished, and will later want to test it on the competition robot.

Hopefully we don't have anything `in-progress` right before a competition, but if we do, then we make one final pull into `competition-test` from `practice-test` before we leave. During the competition, we commit in `competition-test` and pull into `competition-production`. After the competition, we pull from `competition-test` back to `practice-test`, and follow the same procedure as before.
