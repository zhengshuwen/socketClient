package lambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Stream;


public class Test {
	public static void main(String[] args) {
		String[] strings={"String","string1","string2","string3"};
		List<String> strs=Arrays.asList(strings);
		strs.forEach(str->System.out.println(str+";"));
		Optional<String> op=strs.stream().filter(str1->str1.contains("ing2i"))
						.findFirst();
		System.out.println(op.orElse("isNull"));
		Map<String, String> map=new HashMap<String,String>();
		for(int i=0;i<10;i++){
			map.putIfAbsent("i"+i, "hello"+i);
		}
		Iterator<Entry<String, String>> it1=map.entrySet().iterator();
		while(it1.hasNext()){
			Entry<String,String> entry=it1.next();
			System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
		}
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		Iterator<String> it=map.keySet().iterator();
		while(it.hasNext()){
			String key=it.next();
			String value=map.get(key);
			System.out.println("key:"+key+",value:"+value);
		}
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		map.forEach((key,value)->System.out.println("key:"+key+",value:"+value));
	}
}
