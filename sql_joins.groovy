import groovy.transform.Canonical;


AuthorRow[] authors = [[1, "Charles Darwin"], 
					   [2, "George Orwell"],
					   [3, "Richard Dawkins"],];
BooksRow[] books = [[10, "On the Origin of Species", 1],
					[11, "Nineteen Eighty-Four", 2],
					[12, "Foundation", 4]];

printLine()
ResultRow[] result = execInnerJoin(books, authors)
printRows(result)

printLine()
result = execLeftInnerJoin(books, authors)
printRows(result)

printLine()
result = execRightInnerJoin(books, authors)
printRows(result)


/**
 * select b.id, b.name, a.name from books b 
 * inner join authors a on b.authorId = a.id 
 **/
def execInnerJoin(BooksRow[] books, AuthorRow[] authors){
	def result = []

	for(def book in books) {
		for(def author in authors) {
			if (book.authorId == author.id) {
				result += new ResultRow(book.id, book.name, author.name)
			}
		}		
	}

	result
}

/**
 * select b.id, b.name, a.name from books b 
 * left join authors a on b.authorId = a.id 
 **/
def execLeftInnerJoin(BooksRow[] books, AuthorRow[] authors){
	def result = []

	for(def book in books) {
		for(def author in authors) {
			if (book.authorId == author.id) {
				result += new ResultRow(book.id, book.name, author.name)
			}
		}		
	}

	for(def book in books) {
		if (result.every { it.bookId != book.id}){
			result += new ResultRow(book.id, book.name, null)
		}		
	}

	result
}

/**
 * select b.id, b.name, a.name from books b 
 * right join authors a on b.authorId = a.id 
 **/
def execRightInnerJoin(BooksRow[] books, AuthorRow[] authors){
	def result = []
	def usedAuthors = []

	for(def book in books) {
		for(def author in authors) {
			if (book.authorId == author.id) {
				result += new ResultRow(book.id, book.name, author.name)
				usedAuthors.add(author.id)
			}
		}		
	}

	for(def author in authors.findAll { !usedAuthors.contains(it.id) }) {
		result += new ResultRow(null, null, author.name)
	}

	result
}

@Canonical
class BooksRow {
	int id;
	String name;
	int authorId;
}

@Canonical
class AuthorRow {
	int id;
	String name;
}

@Canonical
class ResultRow {
	Integer bookId;
	String book;
	String author
}


void printRows(ResultRow[] rows) {
	for(def row in rows) {
		println "$row.bookId\t$row.book\t$row.author"
	}
}


void printLine() {
	println("-" * 40)
}