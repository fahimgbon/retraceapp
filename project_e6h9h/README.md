# KeyChain - My Personal Project

## "Why is this project of interest to you?"
I rustle through my pockets, frantically searching for my keys after a long day of work;
but they are nowhere to be found. I try to rack my brain, thinking of all the places I’ve been,
the people I’ve walked past and any sudden movements that might have led to my keys falling out of my pockets.
But with all the anxiety, and worrying if I’ll get into the apartment, I’m unable to collect my thoughts.
If only I had documented all the places I had my keys beforehand, 
I wouldn’t have to rely on my shaky memory amid catastrophe. This was the inception of my project idea.

## "What will the application do?"
A space where people are able to have a digital version of their keychain. 
Adding keys as they’re needed, documenting the key’s attributes like its shape, colour, size and 
any other identifiable characteristics. Most importantly, the app will allow the ability to update and 
save the most recent location you recall having your keys on your person. 
And finally, removing keys that aren’t of interest.

## "Who will use it?"
This application is perfect for various users:
- Forgetful college students needing to get into their dorms
- Renters that need to get into their residence
- Landlords and business owners with several keys to keep track of

## User Stories
- As a user, I want to be able to add a key to my keychain (list of keys)
- As a user, I want to be able to view the keys on my keychain 
- As a user, I want to be able to track the locations my key has been
- As a user, I want to be able to remove a key from my keychain 
- As a user, I want to be able to save my keychain to the file and revisit
- As a user, I want to be able to be able to load my keychain from the app

# Citation: Some of the code in this project is inspired by the TellerApp made by the UBC CPSC 210 department

# Citation: Much of the code relating to data persistence has been modeled from:
# the "[JsonSerialization Demo - JsonReader](https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)" 
# file created by the UBC CPSC 210 department

# Citation: Some of the code relating to realizing a GUI has been modeled from the"List Demo Project" 
# (https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java)"
# file in the Java docs


# Instructions for Grader
- You can generate the first required event by filling in the fields and clicking the "Add Key" button to add a key.
- You can generate the second required event by clicking the "Remove Key" button to remove an existing key.
- You can locate my visual component by running the VisualMain class (give it a few seconds to load).
- You can save the state of my application by clicking the "Save Keychain" button.
- You can reload the state of my application by clicking the "Load Keychain" button.

# Phase 4: Task 2
Here is the Event Log:

Wed Aug 10 08:24:12 PDT 2022
Car Key was ADDED to your keychain

Wed Aug 10 08:24:36 PDT 2022
Mail Key was ADDED to your keychain

Wed Aug 10 08:24:38 PDT 2022
Mail Key was REMOVED from your keychain

Wed Aug 10 08:24:40 PDT 2022
Car Key was REMOVED from your keychain

Wed Aug 10 08:24:41 PDT 2022
House Key was REMOVED from your keychain

# Phase 4: Task 3
Overall, my UML diagram doesn't look to have excessive associations. However, some areas of improvement include:

- The ability to have multiple keychains, and by extension thorough testing for those chains; better mimicking reality.
- Allowing users to filter for lost keys.
- Having different data types (beyond String/ArrayList) for Key fields
  - Having an "isLost" field (boolean) for Key. This would help facilitate the tracking/filtering of lost keys.
  - Having a "copy" field (int) for Key if a user has several copies of the same key
- UI Improvements:
  - Including more visual elements, like a splash screen 
  - Adjusting the minimal size for my GUI to upon opening the app display content immediately.
  - Having the locationLog extend vertically so the frame isn't excessively horizontal.
  - Having the window adjust as the size of the locationLog increases.
  - When a location is updated, having the key refresh automatically without the user res-electing key to view change.
- Replacing the "findKey" method in Keychain with overriding Hashcode and Equals; we hadn't learned this during Phase 1.
- Increasing my use of Exceptions to limit the number of "Requires" clauses I have; thus making my code more robust.
- Adjusting locationLog to be a list of custom objects "Location" which contain both the location name and time visited.




