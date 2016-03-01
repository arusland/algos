import groovy.transform.Canonical;


AuthorRow[] authors = [[1, "Charles Darwin"], 
					   [2, "George Orwell"],
					   [3, "Richard Dawkins"],];
BooksRow[] books = [[10, "On the Origin of Species", 1],
					[11, "Nineteen Eighty-Four", 2],
					[12, "Foundation", 4]];

ResultRow[] result = execInnerJoin(books, authors)

printRows(result)

/**
 * select b.id, b.name, a.name from books b 
 * inner join authors a on b.authorId = a.id 
 **/
ResultRow[] execInnerJoin(BooksRow[] books, AuthorRow[] authors){
	ResultRow[] result = [];

	for(def book in books) {
		for(def author in authors) {
			if (book.authorId == author.id) {
				result += new ResultRow(book.id, book.name, author.name)
			}
		}		
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
	int bookId;
	String book;
	String author
}


void printRows(ResultRow[] rows) {
	for(def row in rows) {
		println "$row.bookId\t$row.book\t$row.author"
	}
}