python edit_pos.py $1 pos_ftrs.test
java -cp "../mallet-2.0.7/class:../mallet-2.0.7/lib/mallet-deps.jar" cc.mallet.fst.SimpleTagger --model-file poscrf pos_ftrs.test > out.txt
python edit1.py $1 out.txt $2

python edit_ner.py $1 ner_ftrs.test
java -cp "../mallet-2.0.7/class:../mallet-2.0.7/lib/mallet-deps.jar" cc.mallet.fst.SimpleTagger --model-file nercrf ner_ftrs.test > out.txt
python edit1.py $1 out.txt $3