//ToDo: add MappStruct
//ToDo: add SonarQube
//Exigences du cahier des charges:
//        Pas de tâche dans le passée *
//        Planifier une tâche dont la date de début est au max dans <= 3 jours *
//        Une tâche est peut passer à l’état done si elle ne dépasse pas la date limite *
//        Le candidat ne peut pas demander à l’administrateur que deux modifications (2 jetons)
//        1 jeton par mois pour la suppression
//        Je peux supprimer les tâches que j’ai affecté à moi même sans jetons
//        Le manager ne peut pas laisser une tâche sans être affecté. Si le manager modifie une tâche, la tâche sera intacte.
//        Si le manager n'as pas répondu à une demande de changement de tâches dans un délai de 12 heurs, l'utilisateur doit bénéficier le lendemain d'un solde de jeton de modification en double (4 jetons)
//        Si une tâche dépasse la date limite automatiquement elle se transforme en non done : tous les 24 heures