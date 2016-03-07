# POS and NER tagging of Tweets

Features were identified and attached for efficient labelling of twitter data using a **CRF** and **HMM** 

model tagger MALLET

# Synopsis

For labelling POS and NER tags to twitter data features were first added to the data in the format specified 

by MALLET.Around 72% accuracy had been achieved


Overview of results can be found [here](https://github.com/saivig/PGM/blob/master/OCR-Exh_Inference/Docs/Observations.pdf)

#References

1. [Description](https://github.com/saivig/PGM/blob/master/OCR-Exh_Inference/Docs/Description.pdf)

2. [Part-of-Speech Tagging for Twitter: Annotation, Features, and Experiments](http://www.cs.cmu.edu/~ark/TweetNLP/gimpel+etal.acl11.pdf)

3. [Conditional Random Fields: Probabilistic Models for Segmenting and Labeling Sequence Data](https://people.cs.umass.edu/~mccallum/papers/crf-icml01.ps)

# How to run

Make sure you have MALLET installed first.if not refer [here](http://mallet.cs.umass.edu/download.php) 

To execute code Run command 

>bash run.sh $1 $2 $3

in your source folder where $1 is our input file and $2,$3 will be output files of POS,NER respectively


# Contributors

[Sai Vignan](http://www.iitd.ac.in/~cs5120289)

This work has been done as a part of assignment during course PGM(COL776) - [Prof. Parag Singla](http://www.cse.iitd.ac.in/~parags/)

# License

Copyright (C) 2015  Sai Vignan K

This program is not a free software;If you want to use it please contact me
