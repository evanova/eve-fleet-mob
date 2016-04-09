# eve-fleet-mob
API and Application for managing Eve Online fleets using CREST

###Build

You need:

* Java JDK 8
* Android SDK - use the latest

cd to your cloned repo and run `./gradle build` or `gradlew` depending on your OS.

###Design

There are currently 3 packages:
####crest
Those are CREST JSON-based model objects mapped using Jackson. There is no network code here, only objects and mapping

####data
Don't care about it right now. It's a port of EveMeet entities for later persistence.

####app

It's an android app with the following:

* A model package which contains model objects specific for the application and its UI.
* A Retrofit based implementation of a CREST client which translates CREST model objects to the application ones.
* UI is MVP based. 
* It should be easy to implement more CREST endpoints handling.

###Random Pointers

[Dev Blog - Fleet](https://forums.eveonline.com/default.aspx?g=posts&m=6423855#post6423855)

[Eve 3rd Party Doc](https://eveonline-third-party-documentation.readthedocs.org/en/latest/)

