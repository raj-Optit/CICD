def playbook(inventoryFile, playBook, key1, value1, key2, value2) {

    sh "ansible-playbook -i ${inventoryFile} ${playBook} --extra-vars "${key1}=${value1} ${key2}=${value2}"
}

return this
