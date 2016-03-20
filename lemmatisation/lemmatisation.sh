#!/bin/bash

# ** Script pour lemmatisation : créer un fichier par commentaire contenant le résultat de la lemmatisation ** 

# Création du dossier contenant l'ensemble des fchiers lemmatisés
directoryLemmatisation="../donnees_lemmatisees/";
mkdir -p $directoryLemmatisation;

# Indice unique pour le nom de chaque fichier
indice=1;

# Tant qu'on lit des lignes du fichier CSV (ponctuation + smileys traités)
while IFS='' read -r line || [[ -n "$line" ]]; do
		echo "Traitement du commentaire n°$indice";
		
		# Traitement du commentaire avec treetagger et resultat redirigé dans un fichier texte
		echo $line | tree-tagger-english > $directoryLemmatisation$indice.txt;
		
		# Incrémentation de l'indice
		indice=$((indice+1));
done < "../elements_projet/dataset_for_lemmatisation.csv"

