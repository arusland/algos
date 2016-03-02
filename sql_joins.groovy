import groovy.transform.Canonical;


AuthorRow[] authors = [[1, "Charles Darwin"],
					   [2, "Richard Dawkins"],
					   [3, "George Orwell"]];
BooksRow[] books = [[10, "On the Origin of Species", 1],
					[11, "Foundation", 4],
					[12, "Nineteen Eighty-Four", 3]];

printLine()
ResultRow[] result = execInnerJoin(books, authors)
printRows(result)

printLine()
result = execLeftOuterJoin(books, authors)
printRows(result)

printLine()
result = execRightOuterJoin(books, authors)
printRows(result)

printLine()
result = execFullOuterJoin(books, authors)
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
def execLeftOuterJoin(BooksRow[] books, AuthorRow[] authors){
	def result = []

	for(def book in books) {
		boolean found = false;

		for(def author in authors) {
			if (book.authorId == author.id) {
				result += new ResultRow(book.id, book.name, author.name)
				found = true;
			}
		}

		if (!found){
			result += new ResultRow(book.id, book.name, null)
		}
	}	

	result
}

/**
 * select b.id, b.name, a.name from books b 
 * right join authors a on b.authorId = a.id 
 **/
def execRightOuterJoin(BooksRow[] books, AuthorRow[] authors){
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

/**
 * select b.id, b.name, a.name from books b 
 * full join authors a on b.authorId = a.id 
 **/
def execFullOuterJoin(BooksRow[] books, AuthorRow[] authors){
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

	for(def book in books) {
		if (result.every { it.bookId != book.id}){
			result += new ResultRow(book.id, book.name, null)
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