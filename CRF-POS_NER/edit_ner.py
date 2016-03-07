import sys
import re

sym_tags = {
    '?':'.',
    '"':'\'\'',
    '\'':'\'\'',
    '(':'(',
    ')':')',
    '...':':',
    '&':'CC',
    '&lt':'CC',
    '&lt;':'CC',
    '&amp;':'CC',
    '+':'SYM',
    '|':':',
    '=':'SYM'
    }
company=['port','city','java','Sky','3D','wipro','High','House','Farm','Twitter','Costco','Twitter','YouTube','Evergreen','Subaru','AT&amp;T','MTV','Facebook','Twitter','Twitter','Novel','Wines','Optimise','TMZ','facebook','ufc','TWITTER','Blackberry','facebook','twitter','Electric','Lady','Studios','YouTube','twitter','twitter','UFlex','twitter','iTunes','Techland','YouTube','Sotheby','twitter','target','Forex','Gabriel','Resources','tumblr','EuroVPS','FACEBOOK','CORT','YouTube','Facebook','Playboy','Playboy','YouTube','facebook','YouTube','Visa','iTunes','iTunes','old','navy','TMZ','Business','Alliance','Yahoo','Twitter','Twitter','Microsoft','nintendo','Twitter','Ball','Metal','Container','YouTube','facebook','ngmoco','Cisco','Juniper','twitter','twitter','Marlboro','Ducati','Camelbak','Pepsi','walmart','Super','City','twitter','FB','PAMPERS','YouTube','twitter','gucci','Crocs','South','107','Twitter','YouTube','flipboard','Pedigree','Twitter','Game','Informer','Delphi','Twitter','Facebook','Pizza','Hut','facebook','facebook','you','tube','FedEx','AEG','twitpic','Cyber-Ark','Widro','Subway','LCL','Facebook','Accel','Ione','twitter','VEVO','Ustream','skype','twitter','twitter','Reuters','Pxleyes','Twitter','gucci','YouTube','Brando','Twitter','Engadget','Sky','3D','Starstruck','Daily','Mail','Twitter','Nex-Tech','Nex-Tech','Wireless','Reuters','AMerican','fb','twitter','tumblr','Twitter','Facebook','Chevron']
facility=['CALABASH','LOUNGE','The','Echo','the','Fitzwilliam','Empire','State','Building','ESB','the','Apollo','Empire','State','Building','ESB','Secrets','Champlain','campus','Tipitina','Hotel','Elegante','Costa','Lounge','muse','Southgate','House','Band','hall','Leacock','Museum','Primal','Botanic','Gdns','Sunset','Bistro','St','Johns','PITT','University','Cathedral','of','Learning','G24','Good','Day','Sunshine','Potbelly','Lincoln','Park','MAiZE','lhs','lhs','Mississippi','Studios','Hi','Pac','USM','frat','house','Hattiesburg','the','BLAST','Loveless','Cafe','Swayzes','Venue','Peppers','Town','Square','Place','Casitas','Luna','Lounge','Herbivore','North','Phoenix','ATA','VISIONS','LOUNGE','Stars','belles','mansion','The','Metro','CANWEST','Center','Hustler','Hollywood','Gardens','Futoosh','Rotary','Club','Mai','Tai','Summits','Morgan','Le','Fays','OFF','the','GRID','Fort','Mason','Music','Box','Dim','Mak','@Cromwell','Field','Limelight','Bowl','Long','Island','Forgotten','Door','Stetson','HSBC','center','CAFE','NINE','First','Baptist','Wildcat','Stadium','the','funny','bone','Elements','Dillion','High','School','The','Pub','new','Teaze','cafe','Student','Centre','barksdale','Dicks','New','Life','The','Standard','U.S.','Capitol','building','Knighttime','Billiards','The','Lodge','Disney','world','Disney','World','Spoons','NW','Bistro','BAR','Holyroodhouse','ASU','Step','Gallery']
geoloc=['Cebu','New','Jersey','*Boston*','Delhomme','Nijverdal','Irvine','Houston','Salt','Lake','The','Bay','america','Greenville','AL','Chapel','Hill','Newcastle','Tdot','England','#Dallas','Scotland','HOLLYWOOD','New','York','Long','Island','Mac','Arthur','Airport','NY','Arizona','Gainesville','Chicago','London','Paris','DC','London','Jupiter','Brazil','Fayette','County','SF','San','Mateo','NYC','Chicago','Florida','Wrawby','Belgium','Happy','Valley','Orillia','ON','Nashville','KY','Liffey','Japan','UP','Diliman','miami','Austin','The','Scoot','Inn','Lindis','Pass','Arthurs','Pass','Lewis','Rimutakas','bahamas','Belfast','Sacramento','ASPEN','Hong','Kong','GA','ATL','Eastern','Bloc','US','Rodanthe','Wisconsin','UK','Romania','uk','Chicago','IL','NYC','Central','Park','london','Berkeley','NY','Loyola','Chicago','Verona','Paris','Portland','Texas','#Denver','vancouver','Ohio','Israel','GA','GA','corbin','Slovenia','Newcastle-Upon-Tyne','India','MD','Float-Ellicott','City','MD','MD','California','Vegas','Widener','the','States','bc','Hawaii','Pavonia','Avenue','Newport','Vancouver','D.C.','NYC','Oakland','auburn','japan','KSC','Dayton','NJ','Harrison','Coolum','Fulham','Chicago','America','Canada','Aragon','Spain','kilkenny','tucson','Taorminaa','USA','San','Francisco','Middle','East','S.F.','Indian','Atlanta','Georgia','Mesa','AZ','HICKORY','NC','Naples','Vordingborg','NYC','Robinhoods','bay','Jasmin','aintree','Sydney','Christchurch','garden','grove','Hawaii','Sweden','of','Pi','CWE','Delmar','Kirkwood','alabama','maryland','Viera','orlando','Australia','Copenhagen','Columbus','GA','alderwood','Long','Beach','St','Mary','America','Ft','Mitchell','AK','Patchogue)','Clemson','Auburn','Lancashire','Lancashire','West','Indies','Cahill','Greenville','OH','DC','Vegas','Harvard','LA','Sacramento','CA','Chicago','Giddings','Elgin','AUS','RR','NEW','YORK','delaware','California','inglewood','mv','Hamm','Minneapolis','China','London','Progress','Campus','Scarborough','VT','america','Fenway','Temecula','Vista','del','Lago','orlando','Belgium','LA','Doha','Arizona','Toronto','JPN','GA','Salem','New','York','Pakistan','England','LONDON','SF','LA','NYC','Lelystad','MONTCLAIR','N.J.']
person=['Jan','Brewer','Justin','Bieber','JUTHTIN','BEAVERRRR','Dylan','Lohan','LiLo','Lindsay','Lohan','Joaquin','Phoenix','Andy','Kaufman','Night','Justin','Stephen','Fry','Paco','~Wayne','Dyer','David','Richard','Kadrey','Jackie','JFK','trey','songz','Fes','Scooter','Braun','Bush','Hughie','ODonoghue','Dee','Nina','Chris','Robert','Laughlin','Adam','Beyer','Mandy','Miley','JeA','Unnie','EVA','MENDES','Jim','Rohn','Prophet','Muhammad','Allah','John','Edward','Steve','&quot;','Stylez','&quot;','Desperado','Ben','Mark','Rowan','Robert','pope','Sarah','Jessica','Parker','Gwen','Stefani','Anja','Rubik','Blair','Pope','Brown','-Larry','Bird','Lindsay','Lohan','salsa','caliente','Steve','King','Eduardo','Surita','Pope','Lady','Gaga','diggy','Trisha','Scooter','Braun','Lindsay','Lohan','Justin','Bieber','Donnie','Hathaway','Tony','Blair','Justin','Hendrix','Charles','Larry','Bird','Joel','Ward','Jessica','Simpson','Jessica','Simpson','Simpson','paris','hilton','Don','Mattingly','Joe','Torre','mcfly','sanchez','betos','Justin','bieber','RACHELLE','Rickey','Smiley','JC','God','Lindsay','Lohan','Lohan','Lindsay','Lohan','Shane','Jonah','Claire','Mitchell','Jan','Bloom','mike','George','Hitchcock','Kayak','Magazine','George','Parks','Hitchcock','Charlie','Chaplin','Renee','Lindsay','Lohan','Justin','Bieber','george','carmen','Cudi','Wale','Bush','Obama','Todo','Cabio','Katie','Santy','Justin','Bieber','Marvin','Gaye','Coach','Germano','Albin','Haines','Burrow','Rihanna','Lindsay','Lohan','pete','justin','bieber','God','Alex','Justin','Bieber','Betsey','Johnson','Mr','Van','Jimmie','Johnson','Samuel','Savoirfaire','Williams','Joy','Huhn','Anna','Wintor','Jenny','50','tyson','Kid','Cudi','Mariel','Concepcion','Alexei','Donovan','McNabb','Steve','Christine','Donnell','John','Chiles','Mike','Davis','John','Chiles','Castle','Murkowski','Kerri','russel','George','Parks','George','Parks','Derek','Lindsay','Lohan','Yli','Jarred','Gidley','Eli','Justin','Bieber','Anya','Kamenetz','Charice','jason','GaGa','Kanye','West','Gwen','Stefani','Lindsay','Lohan','Lindsay','Levin','Hien','Fabi','Michael','Kraus','Kathy','Brett','Favre','John','Lennon','Bennett-','Justin','Bieber','PEI','ray','rice','Jackie','JFK','Jacquelin','Dan','Martin','Cheryl','Dermot','Soulja','Boy','Eminem','Chelsea','Handler','-Rumi','Oscar','KRISTEN','BELL','Mike','Lohan','prince','paul','simon','o.d.b.','Shange','Stan','Bowman','Cristobal','Huet','billy','Jorge','Pedersen','Casey','Stoner','Ash','Alex','Amenabar','W/Rachel','Weisz','JL','Teddy','Jerraud','Powers','Jacob','Lacey','joe','Conn','Jennifer','Yanni','chelsea','pope','sfitzy93','al-Mallohi','Wake','Davidson','Charlotte','Tyler','Lewis','Margaret','Hamburg','Penny','Ben','God','Boris','Kodjoe','Abby','emily','rodriguez','Steve','Dave','Mannie','Fresh','Jermaine','Dupri','JIM','JONES','Justin','Bieber','justthen','kenny','Pope','Benedict','John','Henry','Newman','Pope','Benedict','XVI','Tommy','WIseau','Sara','Wonder','Woman','Keppinger','Downs','CJ','Ralph','Lauren','Oscar','de','la','Renta','Gwen','Stefani','-Coco','Chanel','God','God','MADI','Dan','Sam','*Gwen','Stefani','4Dbling','Cooper','Lari','Terry','Fox','Yoav','Tessa','Seanzie','cody','KELE','Soulja','Boy','simone','Justin','Bieber','Rex','Ryan','Santa','John','Donald','Trump','Lord','Jesus','Joe','Joel','Liam','Murkowski','Elvis','Soulja','Boy','Benitez','John','Acuff','Dave','JA','Tim','Bulmer','Mariano','Powell','Daren','Powell','Matt','Riley','Michael','Vick','madison','Michael','Brian','Willow','Smith','Benjamin','Button','Kyle','MJ','nini','Lois','Jessum','justin','bieber','meatloaf','Charlene','Jill','Jemma','charlene','Denise','Calaman','Roy','Greg','Brandy','Lindsay','Caliman','Dave','Matthews','Lea','Brilmayer','Jack','Adam','Nadal','Novak','SCOTT','WEILAND','Beyonce','Ayodhya','Miley','Cyrus','Paris','Hilton','Miley','Cyrus','Rashad','Josh','Herbie','Freddie','Ed','Miliband','Whitney','Ellen','Portia','Lilia','Michael','Jones','Pope','LADY','GAGA','ellwood','john','wertheim','BB','BB','David','Stephen','Fry','Pope','Stephen','john','Scooter','Braun','Kirsten','Baveh','Nate','Pope','Pinky','Mike','Vick','Pope','Clark','Duke','snoop','Mackenna','Bonita','LW','Beleskey','trey','Rob','Jeff','God','Umar','Gul','Marcus','Bentley','Rick','Ross','Drake','Justin','Bieber','Tim','Jason','Wu','Michael','Pope','Lindsay','Lohan','spencer','Crazy','Steve','Carly','Megan','Josh']
product=['SD2','Sandman','Slim','Dior','Sellotape','WoW','iPad','iPhones','Rose','Press','iPhone','iPod','Merlot','Pumpkin','Moonshine','android','Air','Music','Jump','Rock','Band','Guitar','Hero','Music','Jump','Xbox','360','Enslaved','Odyssey','to','the','West','PlayStation','Network','Madden','Google','Music','Kahlua','Club','Penguin','Club','Penguin','green','tea','server','2008','server','2008','R2','Sour','Patch','kids','windows','office','git','Lounge22','Avast','AntiVirus','PROFIONAL','Avast','AntiVirus','4.8','PROFESIONAL','iPad','iPad','IPad','LAMB','Lincoln','park','after','dark','Russian','navy','The','Replacement','Cachupas','Vanilla','vodka','Hoegaarden','Zune','HD','XBOX','iOS','CamelBak','BPA-Free','Better','Bottle','with','Bite','Valve','Coke','HTC','Desire','HTC','Desire','iPad','iPhone','iPhone4','ipod','club','penguin','macdonalds','Bio','Spot','Dortmunder','Gold','Vanquish','PS3','Xbox','360','Conflicts','of','Law','Cases','and','Materials','iPhone','BranchOut','Gnocchi','Italian','Sausage','Swiss','Chard','Tomatoes','Around','The','World','Twice','Morton','s','Salt','chipotle','super','nintendo','sega','genesis']
stopwords_arr=["a","able","about","across","after","all","almost","also","am","among","an","and","any","are","as","at","be","because","been","but","by","can","cannot","could","dear","did","do","does","either","else","ever","every","for","from","get","got","had","has","have","he","her","hers","him","his","how","however","i","if","in","into","is","it","its","just","least","let","like","likely","may","me","might","most","must","my","neither","no","nor","not","of","off","often","on","only","or","other","our","own","rather","said","say","says","she","should","since","so","some","than","that","the","their","them","then","there","these","they","this","tis","to","too","twas","us","wants","was","we","were","what","when","where","which","while","who","whom","why","will","with","would","yet","you","your"]
emoticons=[':)', '<3', '^^', '<3', '-_-', ':)', '<3', ';-)', ':)', ':)', ':)', ':)', '<3', ':)', '\\m/', ':-P', ':)', ':)', '\xe2\x99\xa5', '(:', '-____-', '<3', ':)', '\xe2\x99\xa5', '<3', '(:', ':)', '-_-', ';)', ':)', '\xe2\x99\xa5', '\xe2\x99\xa5', ')', ':)', '\xc2\xaf\\_(\xe3\x83\x84)_/\xc2\xaf', ':O', ':]', ':)', 'P', '^_^', ':P', ':D', '<3', ':', ')', ';-p', ':-/', ';)', '=D', ':)', '(', 'Y', ')', '-__-', ':/', ':(', ':)', ';)', '\xe2\x98\x83', ':)', '-_-', '(:', '=]', '===}}', '<3', ':)', ':)', ':))', ':-)', ':))', ":')", ':)', ';', '3', ';)', ':D', ':(', '(', 'y', ')', '>', ':', 'I', ':)', ':(', '<3', ':', '{', 'o_o', ':)', ';)', '>', '_', '>', '<3', 'xD', ':/', '<333', '&hearts', ':D', 'o_O', '=[[', '\xe2\x99\xa5\xe2\x99\xa1\xe2\x99\xa5', ';)', "\xe2\x80\x8b(\xcb\x87_\xcb\x87'!l", ')', '(', '\xe2\x8c\xa3\xcc\x81_\xe2\x8c\xa3\xcc\x80)', ':)', ':)', ':)', ';)', '<333', 'xDD', 'D:', '-___-', ':/', ':)', ':D', '\xe2\x98\xba', '#k', ':(', ':(', ':)', ':(', '.', '__', ".'", ';)', ':-P', ':(', '-_-', '(', 'Y', ')', 'xD', 'XD', '..:', '(', '(\xc2\xac_\xc2\xac', '"', ')', 'O_o', ':D', ':)', '(=', '-_-', 'XD', ':)', '\xe2\x99\xa5\xe2\x99\xa5\xe2\x99\xa5', '=)', 'X', ')', '(:', '/', ':', ';)', ':', 'S', '\xe2\x99\xa5', ';)', ':D', '-_-', ':P', ';)', ':)', ';)', ':/', ':((', '=)', ':)', '<3', '<3', ':D', ':)', ':)', ':)', '<3', ';(', ':P', ':/', '<33', ':)', '-_-', '\xe0\xb2\xa0\xe2\x97\xa1\xe0\xb2\xa0', ':)', ':', '0', '(', '-_-', '=)', ':p', ':(', ':)', '=)', ':-\\', ':', '3', ';-)', ':)']
def tag_token(token):
    pos = None
    if token in sym_tags:
        pos = sym_tags[token]
    elif token[0] == '@' and len(token) > 1:
        pos = 'usr'
    elif token.lower() == 'rt':
        pos = 'rt'
    elif token[0] == '#':
        pos = 'ht'
    elif token[0:7] == 'http://':
        pos = 'url'
    return pos

def get_features(token):
    ltoken=token.lower()
    ftr_lst = []
    pos=tag_token(ltoken)
    ##f1

    if pos:
        ftr_lst.append('SYMBOL_REGX=' + str(pos))
    #### if usr,rt,ht,url dont add extra features just return it 
    ##f2 
    #### f3 ######
    if len(token)>1 and re.match('^[A-Z]*$', token):
        ftr_lst.append('ALL_CAPS')
    if re.match('[A-Z]', token[0]):
        ftr_lst.append('IS_CAPITALIZED')
    if re.match('.*[0-9].*', token):
        ftr_lst.append('IS_NUM')
    if re.match(r'[0-9]', token):
        ftr_lst.append('SINGLEDIGIT')
    if re.match(r'[0-9][0-9]', token):
        ftr_lst.append('DOUBLEDIGIT')
    if re.match(r'.*-.*', token):
        ftr_lst.append('HASDASH')
    if re.match(r'[.,;:?!-+\'"]', token):
        ftr_lst.append('PUNCTUATION')
    if token in stopwords_arr:
        ftr_lst.append('STOP_WORD')
    if token in emoticons:
        ftr_lst.append('EMOTICON')
    if token[-2:]=="ed":
        ftr_lst.append('END=ed')
    if token[-2:]=="ly":
        ftr_lst.append('END=ly')
    if len(ltoken) >= 4:
        # Get prefixes
        for i in range(1, 5):
                if i <= len(ltoken):
                    ftr_lst.append('PREFIX=' + ltoken[:i])
        # Get suffixes                            
        for i in range(1, 5):
            if i <= len(ltoken):
                ftr_lst.append('SUFFIX=' + ltoken[-1*i:])
    if token in company:
        ftr_lst.append('EX=COMPANY')
    if token in facility:
        ftr_lst.append('EX=FACILITY')
    if token in geoloc:
        ftr_lst.append('EX=GEO-LOC')
    if token in person:
        ftr_lst.append('EX=PERSON')
    if token in product:
        ftr_lst.append('EX=PRODUCT')
    return ftr_lst
######################################### Main Func  ###################
w=open(sys.argv[2],'w')
with open(sys.argv[1]) as r:
    for line in r:
        if line.strip():
            #token=line.split("\n")[0]
            token =line.split("\n")[0]
            # ltoken=token.lower()
            ftr_lst=get_features(token)
            ftr_str=""
            for i in range (len(ftr_lst)):
                ftr_str=ftr_str+ftr_lst[i]+" "
            # ftr_str=ftr_str+ftr_lst[len(ftr_lst)-1]  #taking care of a space syntax
            if (len(ftr_lst)>0):
                line_w=token+" "+ftr_str
                # print(line_w)
                w.write(line_w[:-1]+"\n")
            else:
                w.write(line)
        else:
            w.write("\n")
w.close();

