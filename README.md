# elevator
## problem description
• Provide code that simulates an elevator. You are free to use any language.
• Upload the completed project to GitHub for discussion during interview.
• Document all assumptions and any features that weren’t implemented.
• The result should be an executable, or script, that can be run with the following inputs and generate the following outputs.

`Inputs: [list of floors to visit] (e.g. elevator start=12 floor=2,9,1,32)`
`Outputs: [total travel time, floors visited in order] (e.g. 560 12,2,9,1,32)`
Program Constants: Single floor travel time: 10

## How to run
via command line from this directory
`java -cp ./src/main/java/elevator Elevator start=12 floor=2,9,1,32`


### assumptions
1. No time spent on each floor (ie. time loading passengers)
2. Travel time is identical for each floor travelled
