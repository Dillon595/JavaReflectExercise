package homework;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class Work {

	public static void main(String[] args) throws Exception {
		// 获取类文件
		Class c = Class.forName("java.lang.String");
//		getConstructor(c);
//		System.out.println(getFields(c));
//		System.out.println(getMethods(c));
		StringBuffer sb = new StringBuffer();
		//将包名拼接
		sb.append(getPackage(c));
		//将修饰符拼接
		sb.append(Modifier.toString(c.getModifiers()) + " class ");
		//将类名拼接
		sb.append(c.getSimpleName() + " ");
		//将接口进行拼接
		sb.append(getInterface(c));
		sb.append("{\n");
		//将属性，构造方法和方法全部拼接
		sb.append(getFields(c)).append(getConstructor(c)).append(getMethods(c));

		sb.append("}");
		
		System.out.println(sb);
		//生成的目标文件
		File f = new File("target.java");
		FileWriter fw = new FileWriter(f);
		//将所有字符写出
		fw.write(sb.toString());

		//fw.flush();
		 fw.close();

	}

	/**
	 * 
	 * @Description: 获取指定类的所有构造方法
	 * @author LYL
	 * @date 2021-01-16 15:16:13
	 */
	public static String getConstructor(Class c) {
		// 拿到所有构造方法组成的数组
		Constructor[] ct3 = c.getConstructors();
		// 创建一个字符串缓冲池
		StringBuffer sb = new StringBuffer();
		// 循环遍历所有构造方法
		for (Constructor cc : ct3) {
			// 将构造方法修饰符拼接进入sb
			sb.append(Modifier.toString(cc.getModifiers())).append(" ");
			// 将构造方法名拼接进入sb
			sb.append(cc.getName().substring(cc.getName().lastIndexOf(".") + 1));
			// 将构造方法的左括号拼接进入sb
			sb.append("(");
			// 拿到当前构造方法的所有参数
			Parameter[] pt = cc.getParameters();
			// 如果当前构造方法有参数
			if (pt.length > 0) {
				// 遍历当前构造方法的参数
				for (Parameter par : pt) {
					// 将当前构造方法的参数类型名拼接
					sb.append(par.getType().getSimpleName()).append(" ");
					// 将当前参数类型对应的参数名进行拼接
					sb.append(par.getName()).append(",");
				}
				// 删除最后拼接进去的逗号
				sb.deleteCharAt(sb.length() - 1);
			}
			// 将右括号进行拼接
			sb.append(")");
			// 将左大括号拼接并换行再拼接右大括号，最后再换行
			sb.append(" {").append("\n}\n");
		}
		// 在构造方法最下方换行以和其它方法分开
		sb.append("\n");
		// 将拼好的构造方法字符串返回
		return sb.toString();
	}

	/**
	 * 
	 * @Description: 获取指定类的所有属性
	 * @author LYL
	 * @date 2021-01-16 15:16:41
	 */
	public static String getFields(Class c) {
		// 拿到属性数组
		Field[] fields = c.getDeclaredFields();
		StringBuffer sb = new StringBuffer();
		// 循环遍历所有的属性
		for (Field f : fields) {
			// 拼接属性的修饰符，如： public final static
			sb.append(Modifier.toString(f.getModifiers())).append(" ");
			// 拼接属性的类型名，如：String
			sb.append(f.getType().getSimpleName()).append(" ");
			// 拼接属性名，如：String name中的name
			sb.append(f.getName()).append(";");

			sb.append("\n");
		}
		// 属性拼接完以后多加两个换行，以和其它的方法隔出一段距离
		sb.append("\n");
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * 
	 * @Description: 获取指定类所有的方法
	 * @author LYL
	 * @date 2021-01-16 15:21:24
	 */
	public static String getMethods(Class c) {
		StringBuffer sb = new StringBuffer();
		// 拿到该类的所有方法
		Method[] methods = c.getMethods();
		// 遍历所有的方法
		for (Method md : methods) {
			// 拼接权限修饰符
			sb.append(Modifier.toString(md.getModifiers())).append(" ");
			// 添加返回值类型
			sb.append(md.getReturnType().getSimpleName()).append(" ");
			// 添加方法名
			sb.append(md.getName()).append(" ");

			sb.append("(");
			// 拿到方法的所有参数列表
			Parameter[] pt = md.getParameters();
			// 如果该方法有参数
			if (pt.length > 0) {
				// 循环遍历所有参数
				for (Parameter par : pt) {
					// 参数类型名，如：String
					sb.append(par.getType().getSimpleName()).append(" ");
					// 拼接参数名
					sb.append(par.getName()).append(",");
				}
				// 此时在括号里的最后有一个逗号，此步骤为删去最后的那个逗号
				sb.deleteCharAt(sb.length() - 1);
			}

			sb.append(") ");
			// 拿到异常的数组
			Class[] exceptionTypes = md.getExceptionTypes();
			// 如果有抛出异常
			if (exceptionTypes.length > 0) {
				// 拼接throws
				sb.append("throws ");
				// 遍历所有异常类型
				for (int i = 0; i < exceptionTypes.length; i++) {
					// 拼接异常名
					sb.append(exceptionTypes[i].getSimpleName());
				}
			}
			sb.append(" {\n");
			sb.append("}");
			// 每一个方法后换一行
			sb.append("\n");
		}
		// 所有方法拿到以后在方法的最后加入两个换行符，以和其它部分分隔开
		sb.append("\n");
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * 
	 * @Description: 获取指定类的包名
	 * @author LYL
	 * @date 2021-01-16 15:26:37
	 */
	public static String getPackage(Class c) {
		StringBuffer sb = new StringBuffer();
		// 拿到包名
		sb.append(c.getPackage().toString().substring(0, 17));
		// 区分包名部分和其它成员部分
		sb.append("\n");
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * 
	 * @Description: 拿到指定类的接口
	 * @author LYL
	 * @date 2021-01-16 15:27:48
	 */
	public static String getInterface(Class c) {
		StringBuffer sb = new StringBuffer();
		// 拿到指定类的所有接口
		Class[] cc = c.getInterfaces();
		// str用来装接口名
		String str = null;
		// 如果有接口
		if (cc.length > 0) {
			sb.append("implements ");
			// i计算循环到第几次
			int i = 0;
			// 循环遍历所有接口
			for (Class ccs : cc) {
				// 将接口装入str
				str = ccs.toString();
				// 拿到简化后的接口名
				String temp = str.substring(str.lastIndexOf(".") + 1, str.length());
				// 如果是最后一个接口
				if (i == cc.length - 1) {
					// 不在接口名后加“，” 
					sb.append(temp);
					// 如果不是最后一个接口
				} else {
					// 在接口名后方加上一个“，”
					sb.append(temp).append(", ");
				}
				// 计算此次为第几次循环
				i++;
			}
			// 如果没有接口
		} else {
			// 不往其中添加字符串
			sb.append("");
		}

		return sb.toString();
	}

}
