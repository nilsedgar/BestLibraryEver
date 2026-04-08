Repository returnerar alltid entity, authors/categories som tomma listor
Service fyller på via authorRepository.findByBookId() och categoryRepository.findByBookId()
Mappning tillDTO sker också i service