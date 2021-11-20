import java.util.*;


public class MicahelStuff {

    public static boolean vis[] = new boolean[1000];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        ArrayList<Integer>[] adj= new ArrayList[n+10];
        int root = sc.nextInt();
        char c = sc.next().charAt(0);
        for(int i=0;i<=n+5;i++){
            adj[i]=new ArrayList<Integer>();
        }
        for(int i=0; i<n-1; i++){
            int a = sc.nextInt();
            int b = sc.nextInt();
            adj[a].add(b);
            adj[b].add(a);
        }
        String str = sc.next();
        int[] arr = new int[n+5];
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)==c){
                arr[i+1] = 1;
            }else{
                arr[i+1] = 0;
            }
        }

        arr[root] = dfs(adj, root, arr);

        int ans = 1;
        Arrays.fill(vis, Boolean.FALSE);
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(root);
        vis[root] = true;
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int next: adj[cur]){
                if(vis[next]){
                    continue;
                }
                vis[next] = true;
                if(arr[next] == arr[cur]){
                    ans = next;
                    q.add(next);
                }
            }
        }
        System.out.println(ans + " " + arr[ans]);

    }

    public static int dfs(ArrayList<Integer>[] adj, int cur, int arr[]){
        if(vis[cur]){
            return 0;
        }
        vis[cur] = true;
        if(adj[cur].size()==0){
            return arr[cur];
        }
        for(int x: adj[cur]){
            arr[cur] += dfs(adj, x, arr);
        }
        return arr[cur];
    }

}
