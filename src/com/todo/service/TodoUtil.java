package com.todo.service;

import java.util.*;
import java.io.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("�߰��ϰ���� �׸��� ����: ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("�ߺ��� �����Դϴ�!");
			return;
		}
		sc.nextLine();
		System.out.print("�߰��ϰ���� �׸��� ����: ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("�������� �׸��� ����: ");
		
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		System.out.print("������ ���Ͻô� �׸��� ����: ");

		Scanner sc = new Scanner(System.in);
		
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("������ �������� �ʽ��ϴ�!");
			return;
		}

		System.out.print("�׸��� ���ο� ����: ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("������ �ߺ��� �� �����ϴ�!");
			return;
		}
		
		sc.nextLine();
		System.out.print("�׸��� ���ο� ����: ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�׸��� ������Ʈ �Ǿ����ϴ�!");
			}
		}

	}

	public static void listAll(TodoList l) {
		List list = new ArrayList();
		list = l.getList();
		if (list.isEmpty())
			System.out.println("TodoList�� �������� �ʽ��ϴ�!");
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "]" + "  " + item.getDesc() + "   " + item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter("todolist.txt");
			
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			
			System.out.println("TodoList ����!!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("todolist.txt"));
			
			int number = 0;
			String oneLine;
			while ((oneLine = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneLine, "##");
				String[] buffer = new String[3];
				buffer[0] = st.nextToken();
				buffer[1] = st.nextToken();
				buffer[2] = st.nextToken();
				TodoItem item = new TodoItem(buffer[0], buffer[1], buffer[2]);
				l.addItem(item);
				number++;
			}
			System.out.println(number + "���� �׸��� �о����ϴ�!");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
