# jReference
Hi if you're reading this, please stop because the code you are about to see is absolutely atrocious. 

So what am I supposed to do here?

I guess I'll go ahead and describe whats going on behind the screen.


# How it works

So first, you give the url to the program and select the type of citation. 

AAaaand i just realized i never made an exception to make sure you dont leave the citation selection blank.  
fuck.   
    `//todo: fix error above.`  

Ok, im getting off track.

So after you give the URL, the program then fetches the entire html document of the url (given it is sciencedirect). Using the JSoup html parsing library, the program parses for each element of the page that contains info part of the citation. Each element contains text, which is fetched by JSoup. The text is further formatted and spliced and edited so it fits into the stupid APA guidelines. The final Frankenstein-like string is then pushed through a JTextArea with some html formatting.  

The fact that I worked on this for ~3 weeks is probably why I barely survived the English smartr assessment.

# Support

If you find bugs, please for the love of god, send an email to nguyen.k.allen@gmail.com detailing the bug and if you find it, the code that causes the bug. 

Questions? send them to the same email, its already cluttered and im lonely, so dont be scared to shoot some questions to my empty inbox.  


Shit, its really late. I should probably stop writing this.









